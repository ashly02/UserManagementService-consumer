package com.project.usermanagement_consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import message.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.UserManagementConsumerService;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserManagementConsumerServiceTest {
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    UserManagementConsumerService userManagementConsumerService;
    @Test
    public void testProcessMessage() throws JsonProcessingException {
        ObjectMapper obj=new ObjectMapper();
        Message kafkaMessage=new Message(UUID.randomUUID().toString(),System.currentTimeMillis(),"Create");
        String value=obj.writeValueAsString(kafkaMessage);
        ConsumerRecord<Integer, String> consumerRecord = new ConsumerRecord<>("test-topic", 0, 1L, 123, value);

        when(objectMapper.readValue(consumerRecord.value(), Message.class)).thenReturn(kafkaMessage);

        userManagementConsumerService.processMessage(consumerRecord);
        verify(objectMapper).readValue(consumerRecord.value(),Message.class);

    }
}
