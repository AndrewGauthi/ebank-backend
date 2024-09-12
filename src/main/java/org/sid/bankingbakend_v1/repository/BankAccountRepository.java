package org.sid.bankingbakend_v1.repository;

import org.sid.bankingbakend_v1.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
