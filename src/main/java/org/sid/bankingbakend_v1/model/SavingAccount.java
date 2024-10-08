package org.sid.bankingbakend_v1.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

@DiscriminatorValue("SA")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccount extends BankAccount {

    private double interestRate;
}
