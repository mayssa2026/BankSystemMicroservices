package transactionService.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import transactionService.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
    Transaction save(Transaction transaction);
} 