package org.sid.bankingbakend_v1.web;

import lombok.AllArgsConstructor;
import org.sid.bankingbakend_v1.Services.BankAccountService;
import org.sid.bankingbakend_v1.model.BankAccount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestAPI {

    private BankAccountService bankAccountService;

    //affiche la liste des compte  http://localhost:8085/accounts
    @GetMapping("/accounts")
    public List<BankAccount> listAccount(){
        return bankAccountService.listBankAccount();
    }



}
