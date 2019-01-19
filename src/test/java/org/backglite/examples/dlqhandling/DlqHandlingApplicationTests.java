package org.backglite.examples.dlqhandling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:test.properties")
@ContextConfiguration(locations = {"classpath*:**/spring/dlqh-*.xml",
        "classpath*:**/spring/DlqHandlingApplicationTests-context.xml"})
public class DlqHandlingApplicationTests {

    @Value("${dlq.max_retries}")
    private int dlqMaxRetries;

    @Value("${dlq.retries_delay}")
    private int dlqRetriesDelay;

    @Autowired
    @Qualifier(value = "testOutputObjectChannel")
    MessageChannel testOutputObjectChannel;

    @SpyBean
    @Qualifier(value = "outputRetryChannel")
    MessageChannel outputObjectRetryChannel;

    @SpyBean
    @Qualifier(value = "outputParkingLotChannel")
    MessageChannel outputParkingLotChannel;

    @Test
    public void errorObjectHandling() {
        Message<OtherPOJO> message = MessageBuilder.withPayload(new OtherPOJO("foo")).build();
        testOutputObjectChannel.send(message);

        verify(outputObjectRetryChannel, timeout(dlqRetriesDelay * dlqMaxRetries + 1000).times(dlqMaxRetries)).send(any());

        verify(outputParkingLotChannel, timeout(1000)).send(any());

    }
}
