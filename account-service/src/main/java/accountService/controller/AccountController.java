package accountService.controller;

import org.springframework.web.bind.annotation.*;

import accountService.model.Account;
import accountService.repositories.AccountRepository;
import accountService.service.AccountService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @GetMapping("/{id}/transactions")
    public List<Object> getAccountTransactions(@PathVariable Long id) {
        return accountService.getAccountTransactions(id);
    }

    @PutMapping("/{accountId}/updateBalance")
    public void updateAccountBalance(@PathVariable Long accountId, @RequestParam Double amount) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setBalance(account.getBalance() + amount);  // ✅ Update balance
            accountRepository.save(account);  // ✅ Save updated account
        } else {
            throw new RuntimeException("Account not found!");
        }
    }

}
