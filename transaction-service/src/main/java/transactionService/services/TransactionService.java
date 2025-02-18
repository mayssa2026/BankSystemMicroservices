package transactionService.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import transactionService.feign.AccountClient;
import transactionService.kafka.TransactionProducer;
import transactionService.model.Transaction;
import transactionService.rabbitmq.TransactionQueueProducer;
import transactionService.repositories.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private AccountClient accountClient;
    private final TransactionProducer transactionProducer;
    private final TransactionQueueProducer transactionQueueProducer;
    
    
    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountClient accountClien, TransactionProducer transactionProducer, TransactionQueueProducer transactionQueueProducer) {
        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
        this.transactionProducer = transactionProducer;
        this.transactionQueueProducer = transactionQueueProducer;
    }
   
    @PostMapping
    public Transaction createTransaction(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        transaction = transactionRepository.save(transaction); 

        // ✅ Update account balance based on transaction type
        if ("DEPOSIT".equalsIgnoreCase(transaction.getType())) {
            accountClient.updateAccountBalance(transaction.getAccountId(), transaction.getAmount());
        } else if ("WITHDRAWAL".equalsIgnoreCase(transaction.getType())) {
            accountClient.updateAccountBalance(transaction.getAccountId(), -transaction.getAmount());
        }
        // ✅ Send Kafka event after saving transaction
        String message1 = "Transaction ID: " + transaction.getId() + ", Type: " + transaction.getType() + ", Amount: " + transaction.getAmount();
        transactionProducer.sendTransactionEvent(message1);

        // ✅ Send transaction event to RabbitMQ
        String message2 = "Transaction ID: " + transaction.getId() + ", Type: " + transaction.getType() + ", Amount: " + transaction.getAmount();
        transactionQueueProducer.sendToQueue(message2);

        return transaction;
    }
    
    public List<Transaction> getTransactionsByAccount(Long accountId){
        return transactionRepository.findByAccountId(accountId);
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }
}
