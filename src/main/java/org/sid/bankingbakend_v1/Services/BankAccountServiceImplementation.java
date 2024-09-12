package org.sid.bankingbakend_v1.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.bankingbakend_v1.enums.OperationType;
import org.sid.bankingbakend_v1.exeption.BalanceNotSufficientException;
import org.sid.bankingbakend_v1.exeption.BankAccountNotFoundExcetion;
import org.sid.bankingbakend_v1.exeption.CustomerNotFoundExcetion;
import org.sid.bankingbakend_v1.model.*;
import org.sid.bankingbakend_v1.repository.AccountOperationRepository;
import org.sid.bankingbakend_v1.repository.BankAccountRepository;
import org.sid.bankingbakend_v1.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImplementation implements BankAccountService{

    //methode pour lier les pages **Repository
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;

    // cree un client
    @Override
    public Customer SaveCustomer(Customer customer){
        log.info("Saving new Customer");
        return  customerRepository.save(customer);
    }

    // cree des comptes CurrentAccount
    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundExcetion {
        //recher client par id si il n'existe pas exeption
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundExcetion("Customer no found");
        // passe le donner dans l'entitee CurrentAccount qui heritage de BankAccount
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        // pour sauvegarder utiliser le bankAccountRepository
        return bankAccountRepository.save(currentAccount);
    }

    // cree des comptes SavingAccount
    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundExcetion {
        //recher client par id si il n'existe pas exeption
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null)
            throw new CustomerNotFoundExcetion("Customer no found");
        // passe le donner dans l'entitee SavingAccount qui heritage de BankAccount
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        // pour sauvegarder utiliser le bankAccountRepository
        return bankAccountRepository.save(savingAccount);
    }

    // pour consulter afficher une liste des cliens
    @Override
    public List<Customer> listCustomer(){
        return customerRepository.findAll();
    }

    // consuler un compte
    @Override
    public BankAccount getBankAccount(String customerId) throws BankAccountNotFoundExcetion {
       /* BankAccount bankAccount=bankAccountRepository.findById(customerId).orElse(null);
        if (bankAccount == null)
            throw new BankAccountNotFoundExcetion("BanKAccount not found");*/
        BankAccount bankAccount=bankAccountRepository.findById(customerId)
                .orElseThrow(()->new BankAccountNotFoundExcetion("BanKAccount not found"));
        return bankAccount;
    }

    // realiser un debit sur un compte
    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundExcetion, BalanceNotSufficientException {
        //recher bankAccount par id si il n'existe pas exeption
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundExcetion("BanKAccount not found"));
        //test si il y a sufisament d'argent
        if (bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");

        //ecrit l'operation
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setType(OperationType.DEBIT);
        accountOperationRepository.save(accountOperation);
        //metre ajour le sold du compte
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    // realiser un Cebit sur un compte
    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundExcetion, BalanceNotSufficientException {
        //recher bankAccount par id si il n'existe pas exeption
        BankAccount bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundExcetion("BankAccount not found"));
        //ecrit l'operation
        AccountOperation accountOperation =new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        //metre ajour le solde du compte
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    // Vrirement
    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundExcetion, BalanceNotSufficientException {
        debit(accountIdSource, amount, "Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"tranfer from"+accountIdSource);
    }

    // pour consulter afficher la liste de tous les comptes
    @Override
    public List<BankAccount> listBankAccount(){
        return bankAccountRepository.findAll();
    }

    //pour consulter afficher un clien par id
    @Override
    public Customer getCustomer(Long customerId) throws CustomerNotFoundExcetion {
        Customer customer =customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundExcetion("Customer no found"));
        return customer;
    }

    //pour metre a jour le client customer
    @Override
    public Customer UpdateCustomer(Customer customer){
        log.info("update Customer");
        return customerRepository.save(customer);
    }

    // pour supprimer un customer
    @Override
    public void DeleteCustomer(Long customerId){
        log.info("Delete Customer");
        customerRepository.deleteById(customerId);
    }

    // pour consulter afficher la liste des operation d'un compte
    @Override
    public List<AccountOperation> accountHistory(String accountId){
        List<AccountOperation>accountOperations =accountOperationRepository.findByBankAccountId(accountId);
         return accountOperations;
    }



}
















