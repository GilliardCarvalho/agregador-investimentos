package projeto.bancario.agregadorinvestimentos.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.bancario.agregadorinvestimentos.controller.dto.AccountStockResponseDto;
import projeto.bancario.agregadorinvestimentos.controller.dto.AssociateAccountStockDto;
import projeto.bancario.agregadorinvestimentos.controller.dto.CreateAccountDto;
import projeto.bancario.agregadorinvestimentos.service.AccountService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;


    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }


    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId ,
                                              @RequestBody AssociateAccountStockDto associateAccountStockDto) {
        accountService.associateStock(accountId, associateAccountStockDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> listStocks(@PathVariable("accountId") String accountId) {

        var stocks =  accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }

}
