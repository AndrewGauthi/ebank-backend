package org.sid.bankingbakend_v1.Services;


import org.sid.bankingbakend_v1.exeption.BalanceNotSufficientException;
import org.sid.bankingbakend_v1.exeption.BankAccountNotFoundExcetion;
import org.sid.bankingbakend_v1.exeption.CustomerNotFoundExcetion;
import org.sid.bankingbakend_v1.model.*;

import java.util.List;

public interface BankAccountService {

    // cree un client
    Customer SaveCustomer(Customer customer);

    // cree des comptes CurrentAccount
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundExcetion;

    // cree des comptes SavingAccount
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundExcetion;

    // pour consulter afficher une liste des cliens
    List<Customer> listCustomer();

    // consuler un compte
    BankAccount getBankAccount(String customerId) throws BankAccountNotFoundExcetion;

    // realiser un debit sur un compte
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundExcetion, BalanceNotSufficientException;

    // realiser un Cebit sur un compte
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundExcetion, BalanceNotSufficientException;

    // Vrirement
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundExcetion, BalanceNotSufficientException;

    // pour consulter afficher la liste de tous les comptes
    List<BankAccount> listBankAccount();

    //pour consulter afficher un clien par id
    Customer getCustomer(Long customerId) throws CustomerNotFoundExcetion;

    //pour metre a jour le client customer
    Customer UpdateCustomer(Customer customer);

    // pour supprimer un customer
    void DeleteCustomer(Long customerId);

    // pour consulter afficher la liste des operation d'un compte
    List<AccountOperation> accountHistory(String accountId);





}
