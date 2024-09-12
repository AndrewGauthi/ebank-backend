package org.sid.bankingbakend_v1.repository;

import org.sid.bankingbakend_v1.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
