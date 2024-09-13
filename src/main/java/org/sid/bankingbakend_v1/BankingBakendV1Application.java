package org.sid.bankingbakend_v1;

import org.sid.bankingbakend_v1.Services.BankAccountService;
import org.sid.bankingbakend_v1.exeption.BalanceNotSufficientException;
import org.sid.bankingbakend_v1.exeption.BankAccountNotFoundException;
import org.sid.bankingbakend_v1.exeption.CustomerNotFoundExcetion;
import org.sid.bankingbakend_v1.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class BankingBakendV1Application {

    public static void main(String[] args) {
        SpringApplication.run(BankingBakendV1Application.class, args);
    }

   /* //Test apres creation des entitee
    @Bean
    public CommandLineRunner commandLineRunner(
            CustomerRepository customerRepository,
            BankAccountRepository bankAccountRepository,
            AccountOperationRepository accountOperationRepository
    ){
        return args -> {
            Stream.of("Maricel","Arnaud","Papa","Maman").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(custo -> {
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());//generateur de ID String Automatique
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(custo);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());//generateur de ID String Automatique
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(custo);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(compt->{

                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation =new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setBankAccount(compt);
                    accountOperationRepository.save(accountOperation);

                }
            });






        };
    }*/
    //---------------------
   //Test apres creation des de la couche Service
   @Bean
   CommandLineRunner commandLineRunner( BankAccountService bankAccountService ){
       return args -> {
           //cree une liste contenent des clients
           Stream.of("Maricel","Arnaud","Maman","Papa").forEach(name->{
               Customer customer=new Customer();
               //remplir entitie customer
               customer.setName(name);
               customer.setEmail(name+"@gmail.com");
               bankAccountService.SaveCustomer(customer);

           });
           //pour chaque client on cree des comptes : un CurrentAccount et un SavingAccount
           bankAccountService.listCustomer().forEach(client -> {

               try {
                   //pour chaque clien on cree un compte Courant CurrentAccount
                   bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,client.getId());
                   //pour chaque clien on cree un compte Epagne SavingAccount
                   bankAccountService.saveSavingBankAccount(Math.random()*90000,5.5,client.getId());
               } catch (CustomerNotFoundExcetion e) {
                   throw new RuntimeException(e);
               }
           });

           //pour chaque clien on cree des opreration sur le compte AccountOperation()
           // il faut pour afficher la liste de tous les comptes
           List<BankAccount> bankAccountFindAll = bankAccountService.listBankAccount();
           // video 58.59
           for ( BankAccount bankAccount : bankAccountFindAll){
               for (int i = 0; i < 10; i++) {
                   try {
                       //pour chaque clien on cree des opreration credit
                       bankAccountService.credit(bankAccount.getId(),(Math.random()*12000),"Credit");
                       //pour chaque clien on cree des opreration debit
                       bankAccountService.debit(bankAccount.getId(),Math.random()*9000,"Debit");

                   } catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
                       throw new RuntimeException(e);
                   }
               }
           }

           // video 1:12




       };
   }
    //---------------------
}
