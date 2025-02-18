package accountService.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "transaction-service")
public interface TransactionClient {
    
    @GetMapping("/transactions/account/{accountId}")
    List<Object> getTransactionsByAccount(@PathVariable("accountId") Long accountId);
}
