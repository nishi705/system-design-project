package com.apex.wallet.service;

import com.apex.wallet.dto.AccountRegisterRequestDTO;
import com.apex.wallet.dto.TransferRquestDTO;
import com.apex.wallet.model.Account;
import com.apex.wallet.model.Transaction;
import com.apex.wallet.model.TransactionType;
import com.apex.wallet.repository.AccountRegisterRepository;
import com.apex.wallet.repository.TransactionRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Builder
@Slf4j
public class WalletService {

    @Autowired
    private final AccountRegisterRepository registerRepository;

    @Autowired
    private final TransactionRepository transactionRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    private final String CACHE_ACC_PREFIX = "account";
    private final long CACHE_TTL = 10;


    public String createAccount(AccountRegisterRequestDTO requestDTO){
        Account userInfor = requestDTO.getAccount();
        try {
            Account userAccount = Account.builder()
                    .customerName(userInfor.getCustomerName())
                    .accountNumber(userInfor.getAccountNumber())
                    .balance(userInfor.getBalance())
                    .updatedAt(requestDTO.getAccount().getUpdatedAt())
                    .build();
            userAccount.setId(requestDTO.getId());
            registerRepository.save(userAccount);
        }catch (Exception e){
            return e.getMessage();
        }
        return "user account saved successfully";
    }


    public Optional<Account> findAccountById(Long id){
        Optional<Account> account = registerRepository.findById(id);
        return account;
    }


    public Account deposit(String accountNumber,BigDecimal balance){

        Account account = registerRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance().add(balance));
        registerRepository.save(account);

        //Record Audit Trial

        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .accountId(account.getAccountNumber())
                .amount(balance)
                .transactionType(TransactionType.CREDIT)
                .build();


         transactionRepository.save(transaction);

         return account;
    }

    @Transactional
    public Account withdrew(String accountNumber, BigDecimal balance){

        System.out.println("current thread: " + Thread.currentThread().getName() + "is processing");

       // Account account = registerRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account is not found"));
        Account account = registerRepository.findByAccountNumberForUpdate(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(balance) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(balance));
        registerRepository.save(account);


        //Intentional crash simulation
//        if(true){
//            throw  new RuntimeException("Simulate Server/Network crashed");
//        }

        //Record Audit trial

        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .accountId(account.getAccountNumber())
                .amount(balance)
                .transactionType(TransactionType.DEBIT)
                .build();

        transactionRepository.save(transaction);
        return account;
    }


    //this is the method were deadlock  arises
    //because at the same first thread locked the user1 account for transferring the money
    //second thread locked the user2 account for transferring the money
    @Transactional
    public void transferMoneyNaiveDeadlock(TransferRquestDTO requestDTO){
        String fromAcc = requestDTO.getFromAccountNumber();
        String toAcc = requestDTO.getToAccountNumber();
        BigDecimal amount = requestDTO.getAmount();

        //fetch and lock the senders account
        //Account senderAccount = registerRepository.findByAccountNumberForUpdate(fromAcc).orElseThrow(() -> new RuntimeException("No Account found for the sender"));

          Account senderAccount = registerRepository.findByAccountNumberWithTimeout(fromAcc).orElseThrow(() -> new RuntimeException("senders account not available"));

          //Fetch and lock receivers account
       // Account receiverAccount = registerRepository.findByAccountNumberForUpdate(toAcc).orElseThrow(() -> new RuntimeException("No account found for receiver"));

        Account receiverAccount = registerRepository.findByAccountNumberWithTimeout(toAcc).orElseThrow(() -> new RuntimeException("No account found for receiver"));

        //validate the balance
        if(senderAccount.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Insufficient balance");
        }

        //update balance mutation
        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

        //save the amount in account table
        registerRepository.save(senderAccount);
        registerRepository.save(receiverAccount);

        //Log a DEBIT transaction for the sender
        Transaction senTransaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .accountId(fromAcc)
                .amount(amount)
                .transactionType(TransactionType.DEBIT)
                .build();

        //Log the CREDIT transaction for the receiver
        Transaction recTransaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .accountId(toAcc)
                .amount(amount)
                .transactionType(TransactionType.CREDIT)
                .build();

        transactionRepository.save(senTransaction);
        transactionRepository.save(recTransaction);
    }

    /*
    With Strategy 1 (Lexicographical Ordering),
    we fixed the problem by forcing our application code to lock rows in a specific order.
     */
    @Transactional
    public void transferMoneyToAccount(String fromAccountNumber, String toAccountNumber, BigDecimal amount){

        if(fromAccountNumber.equals(toAccountNumber)){
            throw new RuntimeException("Cannot transfer the money at the same account: ");
        }

        Account firstAccountLock;
        Account secondAccountLock;

        boolean isFromSmaller = fromAccountNumber.compareTo(toAccountNumber) < 0;

        if(isFromSmaller){
            firstAccountLock = registerRepository.findByAccountNumberForUpdate(fromAccountNumber).orElseThrow(() -> new RuntimeException("senders account not found"));
            secondAccountLock = registerRepository.findByAccountNumberForUpdate(toAccountNumber).orElseThrow(() -> new RuntimeException("receivers account not found"));
        }else{
             firstAccountLock = registerRepository.findByAccountNumberForUpdate(toAccountNumber).orElseThrow(() -> new RuntimeException("receivers account not found"));
             secondAccountLock = registerRepository.findByAccountNumberForUpdate(fromAccountNumber).orElseThrow(() -> new RuntimeException("senders account not found"));
        }

       Account sender = fromAccountNumber.equals(firstAccountLock.getAccountNumber()) ? firstAccountLock : secondAccountLock;
       Account receiver = fromAccountNumber.equals(firstAccountLock.getAccountNumber()) ? secondAccountLock : firstAccountLock;

       if(sender.getBalance().compareTo(amount) < 0){
           throw  new RuntimeException("Insufficient balance");
       }

        // 5. Mutate state in memory (Hibernate dirty checking handles the updates)
       sender.setBalance(sender.getBalance().subtract(amount));
       receiver.setBalance(receiver.getBalance().add(amount));

       // 6. Log the Audit Ledger Entries
       Transaction debitx = Transaction.builder()
               .transactionId(UUID.randomUUID().toString())
               .accountId(sender.getAccountNumber())
               .transactionType(TransactionType.DEBIT)
               .amount(amount)
               .build();

       Transaction creditx = Transaction.builder()
               .transactionId(UUID.randomUUID().toString())
               .accountId(receiver.getAccountNumber())
               .transactionType(TransactionType.DEBIT)
               .amount(amount)
               .build();

       transactionRepository.save(debitx);
       transactionRepository.save(creditx);

    }

    public Account findFromRedisCache(String accountNumber){
        String cacheKey = CACHE_ACC_PREFIX + accountNumber;


        try{
            Account cacheAccount = (Account) redisTemplate.opsForValue().get(cacheKey);
            if(cacheAccount != null){
             log.info("CACHE HIT: Retrieved the account from the redis"+ accountNumber);
             return cacheAccount;
            }
        }catch(Exception e){
           log.error("Account not found  in redis"+ e.getMessage());
            // Fallback gracefully to DB if cache engine is down (Fault Tolerance)
        }

        //2.cache miss : fetch the data from the postgress
        log.info("CACHE MISS: fetching account from the postgresdb");
        Account account = registerRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));

        //set the account to resdis
        /*
        True Asynchronous implementation
        To make this step truly asynchronous, senior engineers offload that write
        task to a separate background thread pool using Spring's @Async or a Java
        CompletableFuture, like this:
        */
       // CompletableFuture.runAsync(() -> {
            try {
                redisTemplate.opsForValue().set(cacheKey, account, java.time.Duration.ofMinutes(CACHE_TTL));
                log.info("CACHE POPULATED: Account {} saved to Redis with 10 min TTL", accountNumber);
            } catch (Exception e) {
                log.info("redis write error" + e.getMessage());
            }
        //});
        return account;

    }

    // NOTE: In your balance mutation methods (like withdraw/transfer),
    // you must Evict/Delete the cache key when data changes to prevent stale data!
    private void evictCache(String accountNumber){
        redisTemplate.delete(CACHE_ACC_PREFIX + accountNumber);
    }
}
