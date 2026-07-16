package com.payment_tracker;

import com.payment_tracker.customeexception.PaymentNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/standalone-tracker")
public class PaymentController {
    private final Map<Long, String> transactionLog = new ConcurrentHashMap<>();




    //404 not found
    @GetMapping("/transactions/{txId}")
    public ResponseEntity<String> getTransactionId(@PathVariable Long txId){

        if(!transactionLog.containsKey(txId)){
            throw new PaymentNotFoundException("transaction log with id" + txId + "not found");
        }

        return ResponseEntity.ok("status"+ transactionLog.get(txId));

    }


}
