package consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import service.UserManagementConsumerService;

@Component
public class UserManagementConsumer {
    private final UserManagementConsumerService userManagementConsumerService;

    @Autowired
    public UserManagementConsumer(UserManagementConsumerService userManagementConsumerService){
        this.userManagementConsumerService=userManagementConsumerService;
    }
    private static final Logger log = LoggerFactory.getLogger(UserManagementConsumer.class);

    @KafkaListener(topics = {"usermanagement-events1"}, groupId = "mygroup")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws Exception {
        try {
            userManagementConsumerService.processMessage(consumerRecord);
        } catch (Exception e) {
            throw new Exception("Error receiving user event");
        }
    }
}
