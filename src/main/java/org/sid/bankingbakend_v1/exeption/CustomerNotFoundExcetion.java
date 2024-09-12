package org.sid.bankingbakend_v1.exeption;

public class CustomerNotFoundExcetion extends Throwable {

    public CustomerNotFoundExcetion(String message) {
        super(message);
    }
}
