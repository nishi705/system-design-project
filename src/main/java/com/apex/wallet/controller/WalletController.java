package com.apex.wallet.controller;

import com.apex.wallet.dto.*;
import com.apex.wallet.dto.ResponseStatus;
import com.apex.wallet.model.Account;
import com.apex.wallet.repository.AccountRegisterRepository;
import com.apex.wallet.service.RateLimiterService;
import com.apex.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class WalletController {

    @Autowired
    WalletService walletService;

    @Autowired
    AccountRegisterRepository repository;

    @Autowired
    RateLimiterService rateLimiterService;


    @PostMapping("/user/registerUser/")
    public AccountRegisterResponseDTO registerAccount(@RequestBody AccountRegisterRequestDTO requestDTO){
        AccountRegisterResponseDTO responseDTO = new AccountRegisterResponseDTO();
        try{
            String message = walletService.createAccount(requestDTO);
            responseDTO.setMessage(message);
            responseDTO.setAccountHolderName(requestDTO.getAccount().getCustomerName());
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(StatusCode.SUCCESS);
            responseDTO.setResponseStatus(responseStatus);
        }catch(Exception e){
            responseDTO.setMessage("User not saved successfully");
            ResponseStatus responseStatus = new ResponseStatus();
            responseStatus.setStatusCode(StatusCode.SUCCESS);
            responseDTO.setResponseStatus(responseStatus);
        }

        return responseDTO;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBalance(@PathVariable Long id, @RequestBody UpdateBalanceRequestDTO requestBalance){

        Account account = walletService.findAccountById(id).orElseThrow();

        account.setBalance(requestBalance.getBalance());

        repository.save(account);

        return ResponseEntity.ok("updated balance successfully");

    }

    //@RequestParam
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<String> depositMoney(@PathVariable String accountNumber,@RequestBody UpdateBalanceRequestDTO updateBalanceRequestDTO){
         walletService.deposit(accountNumber,updateBalanceRequestDTO.getBalance());
         return ResponseEntity.ok("Amount saved successfully");

    }

   @PostMapping("/{accountNumber}/withdrew")
   public ResponseEntity<Account> withdrew(@PathVariable String accountNumber, @RequestParam BigDecimal amount){

        return ResponseEntity.ok(walletService.withdrew(accountNumber, amount));

   }

   @PostMapping("/transfer")
    public ResponseEntity<String> executeTransfer(@RequestBody TransferRquestDTO requestDTO,
                                                  @RequestHeader(value = "X-Client-Id", defaultValue = "anonymous") String clientId){

        // 1. Evaluate rate limiter state first
       if(!rateLimiterService.isAllowed(clientId)){
           return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                   .body("Rate limit exceeded: please try after 1 minute");
       }

       walletService.transferMoneyNaiveDeadlock(requestDTO);
       return ResponseEntity.ok("Transfer completed successfully");
   }

}
