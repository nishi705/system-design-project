package com.apex.wallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRegisterResponseDTO {
    private ResponseStatus responseStatus;
    private String message;
    private String accountHolderName;
}
