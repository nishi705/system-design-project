package com.apex.wallet.repository;

import com.apex.wallet.model.Account;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRegisterRepository extends JpaRepository<Account, Long> {

    @Override
    Account save(Account account);

    @Override
    Optional<Account> findById(Long id);

    Optional<Account> findByAccountNumber(String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM  Account a where a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumberForUpdate(@Param("accountNumber") String accountNumber);


    //The timeout value is defined in milliseconds.
    //    // "0" maps directly to PostgreSQL's NOWAIT behavior (fail instantly if locked)

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    //JPA Lock Timeout
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "500")})//500 ms-> milli second
    @Query("SELECT a FROM Account a where a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumberWithTimeout(@Param("accountNumber") String accountNumber);

}
