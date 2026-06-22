package com.apex.wallet.dto;

import com.apex.wallet.model.Account;
import com.apex.wallet.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRegisterRequestDTO extends BaseModel {
    private Account account;
//    private String customerName;
//    private String accountNumber;
//    private BigDecimal balance;

}
