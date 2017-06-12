package com.example.processing;

import com.example.domain.Answer;
import com.example.domain.Error;
import com.example.domain.Message;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by Sergey Shushkov on 10.06.2017.
 * ClearScale Java Team
 */
public class MessageProcessorTest extends Assert {

    private MessageProcessor messageProcessor;

    @BeforeTest
    public void setUp() {
        messageProcessor = new MessageProcessor();
        messageProcessor.setPropFilename("server.properties");
        messageProcessor.init();
    }


    @Test
    public void sumServiceTest() {
        Message message = new Message("SumService", "sum", new Object[]{4, 2}, 1, new Class[]{Integer.class, Integer.class});
        Answer answer = messageProcessor.proccessMessage(message);

        assertNotNull(answer);
        assertEquals(answer.getResult(), 6);

    }

    @Test
    public void nullMessageTest() {
        Answer answer = messageProcessor.proccessMessage(null);

        assertNotNull(answer);
        assertTrue(answer.getResult() instanceof Error);

    }

    @Test
    public void nullServiceTest() {
        Message message = new Message(null, "brokenSum", null, 1, null);
        Answer answer = messageProcessor.proccessMessage(message);

        assertNotNull(answer);
        assertTrue(answer.getResult() instanceof Error);

    }

    @Test
    public void nullMethodTest() {
        Message message = new Message("SumService", null, null, 1, null);
        Answer answer = messageProcessor.proccessMessage(message);

        assertNotNull(answer);
        assertTrue(answer.getResult() instanceof Error);

    }

    @Test
    public void noSuchServiceTest() {
        Message message = new Message("test_service", "brokenSum", null, 1, null);
        Answer answer = messageProcessor.proccessMessage(message);

        assertNotNull(answer);
        assertTrue(answer.getResult() instanceof Error);
    }


    @Test
    public void noSuchMethodTest() {
        Message message = new Message("SumService", "brokenSum", null, 1, null);
        Answer answer = messageProcessor.proccessMessage(message);

        assertNotNull(answer);
        assertTrue(answer.getResult() instanceof Error);

    }

}
