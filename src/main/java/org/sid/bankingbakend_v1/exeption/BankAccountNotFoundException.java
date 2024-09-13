package org.sid.bankingbakend_v1.exeption;

public class BankAccountNotFoundException extends Exception {

    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
