package com.apex.wallet;

import com.apex.wallet.dto.TransferRquestDTO;
import com.apex.wallet.model.Account;
import com.apex.wallet.repository.AccountRegisterRepository;
import com.apex.wallet.repository.TransactionRepository;
import com.apex.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@ActiveProfiles("test")
public class WalletServiceConcurrencyTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private AccountRegisterRepository accountRegisterRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    private String testAccountNumber;

    @BeforeEach

    void setup(){
        accountRegisterRepository.deleteAll();
        transactionRepository.deleteAll();

        testAccountNumber = "ACC_CONCURRENCY_TEST";
        Account account = Account.builder()
                .accountNumber(testAccountNumber)
                .customerName("RACE_CONDITION_TEST")
                .balance(BigDecimal.valueOf(1000.00))
                .build();
             accountRegisterRepository.save(account);
    }

    @Test
    public void testWithdrewServiceInRaceConsdition() throws InterruptedException {
        int threadSize = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
        CountDownLatch latch = new CountDownLatch(1);

        for(int i=0;i<threadSize;i++){

            executorService.submit(() -> {
                try{
                   latch.await();
                   walletService.withdrew(testAccountNumber, new BigDecimal("1000.000"));
                }catch(Exception e){
                    System.out.println("exception got caught while execution: "+ e.getMessage());
                }
            });
        }

        latch.countDown();

        executorService.shutdown();

        // Wait up to 5 seconds for them to hit the DB and finish
        boolean finishedCleanly = executorService.awaitTermination(5, TimeUnit.SECONDS);

        if (!finishedCleanly) {
            System.out.println("Threads did not finish within the time limit!");
        }
        Account updatedAcount = accountRegisterRepository.findByAccountNumber(testAccountNumber).orElseThrow();

        //Final database record log
        System.out.println("updated account balance is: "+ updatedAcount.getBalance());
        System.out.println("Transaction log is: "+ transactionRepository.count());

    }

    //multithreaded deadlock testcase
    @Test
    public void testP2PTransferDeadlock() throws InterruptedException {
        String NishiAccNumber = "NISHI_ACC";
        String SumanAccNumber = "SUMAN_ACC";

        accountRegisterRepository.deleteAll();
        transactionRepository.deleteAll();


        Account nishiAcc = Account.builder()
                .accountNumber(NishiAccNumber)
                .customerName("Nishi")
                .balance(BigDecimal.valueOf(500.00))
                .build();

        Account sumanAcc = Account.builder()
                .accountNumber(SumanAccNumber)
                .customerName("Suman")
                .balance(BigDecimal.valueOf(500.00))
                .build();

        accountRegisterRepository.save(nishiAcc);
        accountRegisterRepository.save(sumanAcc);


        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        //Crucial Correction: The Main Thread does not execute the code inside the submit(() -> { ... }) block.
        // It simply passes that block of code to the Executor Pool and moves on.
        for(int i=0;i<2;i++){
            executorService.submit(() -> {
                try{
                  latch.await();
                 TransferRquestDTO transferRquestDTO = new TransferRquestDTO(NishiAccNumber,SumanAccNumber,BigDecimal.valueOf(100.00));
                  walletService.transferMoneyNaiveDeadlock(transferRquestDTO);

                } catch (Exception e) {
                    System.out.println("exception got caught while execution: "+ e.getMessage());

                }
            });

            executorService.submit(() ->{
                try{
                    latch.await();
                    TransferRquestDTO transferRquestDTO = new TransferRquestDTO(SumanAccNumber,NishiAccNumber,BigDecimal.valueOf(50.00));
                    walletService.transferMoneyNaiveDeadlock(transferRquestDTO);
                }catch (Exception e){
                    System.out.println("Exception occured while processing thread2: "+ e.getMessage());
                }
            });
        }

        latch.countDown();

        executorService.shutdown();
        boolean finishedCleanly = executorService.awaitTermination(5, TimeUnit.SECONDS);

        if(!finishedCleanly){
            System.out.println("thread did not finished within the time limit");
        }


    }


    @Test
    public void lexicographicalLockTestInDeadLock(){

        String receiversAccountNumber = "ACC_REC_NUMBER";

        Account receiverAccount = Account.builder()
                .accountNumber(receiversAccountNumber)
                .customerName("Receiver Test")
                .balance(BigDecimal.ZERO)
                .build();
        accountRegisterRepository.save(receiverAccount);

        walletService.transferMoneyToAccount(testAccountNumber, receiversAccountNumber, BigDecimal.valueOf(300.00));

        Account updatedReceiver = accountRegisterRepository.findByAccountNumber(receiversAccountNumber).orElseThrow();
        Account updateSender = accountRegisterRepository.findByAccountNumber(testAccountNumber).orElseThrow();

        System.out.println("Updated balance in senders account: "+ updateSender.getBalance());
        System.out.println("Updated balance in receivers acount: "+ updatedReceiver.getBalance());

    }

    @Test
    public void testDeadlockStrategyWithTimeout() throws InterruptedException {
        // 1. Setup two clean accounts
        String user1Acc = "ACC_USER_1";
        String user2Acc = "ACC_USER_2";

        // Clean old data
        accountRegisterRepository.deleteAll();
        transactionRepository.deleteAll();

        Account user1 = Account.builder().accountNumber(user1Acc).customerName("User 1").balance(new BigDecimal("1000.00")).build();
        Account user2 = Account.builder().accountNumber(user2Acc).customerName("User 2").balance(new BigDecimal("1000.00")).build();

        accountRegisterRepository.save(user1);
        accountRegisterRepository.save(user2);

        // 2. Setup the Executor pool for 2 concurrent colliding transfers
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        // Thread A: User 1 tries to send 100 to User 2
        executorService.submit(() -> {
            try {
                latch.await();
                TransferRquestDTO dto = new TransferRquestDTO(user1Acc, user2Acc, new BigDecimal("100.00"));
                walletService.transferMoneyNaiveDeadlock(dto);
            } catch (Exception e) {
                System.out.println("THREAD A EXCEPTION CAUGHT: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
            }
        });

        // Thread B: User 2 simultaneously tries to send 50 to User 1 (Opposite direction!)
        executorService.submit(() -> {
            try {
                latch.await();
                TransferRquestDTO dto = new TransferRquestDTO(user2Acc, user1Acc, new BigDecimal("50.00"));
                walletService.transferMoneyNaiveDeadlock(dto);
            } catch (Exception e) {
                System.out.println("THREAD B EXCEPTION CAUGHT: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
            }
        });

        // 3. Fire the starting gun!
        latch.countDown();

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("=========================================");
        System.out.println("Final total transactions logged: " + transactionRepository.count());
        System.out.println("=========================================");
    }
}

