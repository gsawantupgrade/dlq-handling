package org.backglite.examples.dlqhandling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:test.properties")
@ContextConfiguration(locations = {"classpath*:**/spring/dlqh-*.xml",
        "classpath*:**/spring/DlqHandlingApplicationTests-context.xml"})
public class DlqHandlingApplicationTests {

    @Autowired
    @Qualifier(value = "testOutputObjectChannel")
    MessageChannel testOutputObjectChannel;

    @Test
    public void errorObjectHandling() throws InterruptedException {
        Message<String> message = MessageBuilder.withPayload("foo").build();
        testOutputObjectChannel.send(message);

        Thread.currentThread().sleep(60000);
    }
}
