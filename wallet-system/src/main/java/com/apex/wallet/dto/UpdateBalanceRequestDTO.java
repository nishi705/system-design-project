package com.apex.wallet.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateBalanceRequestDTO {
    private BigDecimal balance;
}
