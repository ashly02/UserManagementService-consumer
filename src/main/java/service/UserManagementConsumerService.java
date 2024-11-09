package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import message.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagementConsumerService {
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(UserManagementConsumerService.class);
    @Autowired
    public UserManagementConsumerService(ObjectMapper objectMapper){
        this.objectMapper=objectMapper;
    }
    public void processMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        Message kafkaMessage=objectMapper.readValue(consumerRecord.value(), Message.class);
        log.info("Consumed message: {}",kafkaMessage);
    }
}
