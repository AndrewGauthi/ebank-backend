package org.sid.bankingbakend_v1.exeption;

public class BalanceNotSufficientException extends Throwable {

    public BalanceNotSufficientException(String message) {

        super(message);
    }
}
