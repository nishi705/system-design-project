package com.apex.wallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "account")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account extends BaseModel{

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
