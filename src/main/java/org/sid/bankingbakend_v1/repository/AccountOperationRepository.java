package org.sid.bankingbakend_v1.repository;

import org.sid.bankingbakend_v1.model.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

    //pour consulter les opperation d'un compte
    List<AccountOperation> findByBankAccountId(String accountId);
}
