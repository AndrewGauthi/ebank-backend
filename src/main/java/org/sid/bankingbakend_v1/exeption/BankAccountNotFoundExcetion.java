package org.sid.bankingbakend_v1.exeption;

public class BankAccountNotFoundExcetion extends Throwable {

    public BankAccountNotFoundExcetion(String message) {
        super(message);
    }
}
