package org.sid.bankingbakend_v1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.bankingbakend_v1.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Entity

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@DiscriminatorColumn(name = "TYPE",length = 4)

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    @Id
    private  String id;
    private Date createdAt;
    double balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne
    Customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;
}
