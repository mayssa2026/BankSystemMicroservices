
package accountService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accountService.feign.TransactionClient;
import accountService.model.Account;
import accountService.repositories.AccountRepository;

import java.util.List;
import java.util.Optional;


@Service
public class AccountService {

    @Autowired
    private TransactionClient transactionClient;
    
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public List<Object> getAccountTransactions(Long accountId) {
        return transactionClient.getTransactionsByAccount(accountId);
    }
}
