package transactionService.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountClient{
    @GetMapping("/accounts/{id}")
    boolean checkAccountExists(@PathVariable("id") Long id);
    @PutMapping("/accounts/{accountId}/updateBalance")
    void updateAccountBalance(@PathVariable("accountId") Long accountId, @RequestParam("amount") Double amount);
} 





