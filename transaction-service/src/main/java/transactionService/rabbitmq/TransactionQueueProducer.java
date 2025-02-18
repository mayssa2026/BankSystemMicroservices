package transactionService.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    public TransactionQueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(String message) {
        rabbitTemplate.convertAndSend("transaction-queue", message);
        System.out.println("Sent RabbitMQ message: " + message);
    }
}
