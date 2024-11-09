package com.project.usermanagement_consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import consumer.UserManagementConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.UserManagementConsumerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserManagementConsumerTest {

    @Mock
    UserManagementConsumerService userManagementConsumerService;

    @InjectMocks
    UserManagementConsumer userManagementConsumer;

    @Test
    public void testOnMessage() throws Exception {
        ConsumerRecord<Integer, String> consumerRecord = new ConsumerRecord<>("test-topic", 0, 1L, 123, "test-value");

        userManagementConsumer.onMessage(consumerRecord);

        verify(userManagementConsumerService).processMessage(consumerRecord);
    }

    @Test
    public void testOnMessage_exception() throws JsonProcessingException {
        ConsumerRecord<Integer, String> consumerRecord = new ConsumerRecord<>("test-topic", 0, 1L, 123, "test-value");
        doThrow(new RuntimeException("Test exception")).when(userManagementConsumerService).processMessage(consumerRecord);
        Exception e= assertThrows(Exception.class, () -> {
            userManagementConsumer.onMessage(consumerRecord);
        });
        assertEquals("Error receiving user event",e.getMessage());
    }

}
