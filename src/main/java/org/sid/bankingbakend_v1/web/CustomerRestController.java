package org.sid.bankingbakend_v1.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.bankingbakend_v1.Services.BankAccountService;
import org.sid.bankingbakend_v1.model.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {

    private BankAccountService bankAccountService;

    //je veux consulter la liste des clients http://localhost:8085/customers
    @GetMapping("/customers")
    public List<Customer> customers(){
        return bankAccountService.listCustomer();
    }


}
