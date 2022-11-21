package core.basesyntax.dao.impl;

import core.basesyntax.AbstractTest;
import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageDaoImplTest extends AbstractTest {
    private MessageDao messageDao;

    @Before
    public void setUp() {
        messageDao = new MessageDaoImpl(getSessionFactory());
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[] {
                Message.class,
                MessageDetails.class
        };
    }

    @Test
    public void createMessage_NoMessageDetails_Ok() {
        Message message = new Message();
        message.setContent("Welcome message");
        Message actual = messageDao.create(message);
        Assert.assertNotNull("Check you have implemented the `create` method " +
                "in the MessageDaoImpl class", actual);
        Assert.assertNotNull(actual.getId());
        Assert.assertNotNull(actual.getContent());
        Assert.assertEquals(1L, actual.getId().longValue());
        Assert.assertEquals("Welcome message", actual.getContent());
    }

    @Test
    public void createMessage_WithDetails_Ok() {
        Message message = new Message();
        message.setContent("Welcome message");
        MessageDetails details = new MessageDetails();
        details.setSender("Bob");
        message.setMessageDetails(details);
        // Verify you have implemented `create` method in the MessageDao,
        // or check the CASCADE configuration
        Message actual = messageDao.create(message);
        Assert.assertNotNull("Check you have implemented the `create` method " +
                "in the MessageDaoImpl class", actual);
        Assert.assertNotNull(actual.getId());
        Assert.assertNotNull(actual.getContent());
        Assert.assertEquals(1L, actual.getId().longValue());
        Assert.assertEquals("Welcome message", actual.getContent());
        Assert.assertNotNull(actual.getMessageDetails());
        Assert.assertNotNull(actual.getMessageDetails());
        Assert.assertNotNull(actual.getMessageDetails().getId());
        Assert.assertEquals(1L, actual.getMessageDetails().getId().longValue());
        Assert.assertEquals("Bob", actual.getMessageDetails().getSender());
    }

    @Test
    public void getById_NoMessageDetails_Ok() {
        Message message = new Message();
        message.setContent("Welcome message");
        Message created = messageDao.create(message);
        Assert.assertNotNull("Check you have implemented the `create` method " +
                "in the MessageDaoImpl class", created);
        Assert.assertNotNull(created.getId());
        Assert.assertNotNull(created.getContent());

        Message actual = messageDao.get(1L);
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getId());
        Assert.assertEquals(1L, actual.getId().longValue());
        Assert.assertEquals("Welcome message", actual.getContent());
    }

    @Test
    public void getAll_NoMessageDetails_Ok() {
        // Save two messages into the DB
        Message welcomeMessage = new Message();
        welcomeMessage.setContent("Welcome message");
        Message createdWelcome = messageDao.create(welcomeMessage);
        Assert.assertNotNull("Check you have implemented the `create` method " +
                "in the MessageDaoImpl class", createdWelcome);
        Assert.assertNotNull(createdWelcome.getId());
        Assert.assertNotNull(createdWelcome.getContent());

        Message goodbyeMessage = new Message();
        goodbyeMessage.setContent("Goodbye message");
        Message createdGoodbye = messageDao.create(goodbyeMessage);
        Assert.assertNotNull("Check you have implemented the `create` method " +
                "in the MessageDaoImpl class", createdGoodbye);
        Assert.assertNotNull(createdGoodbye.getId());
        Assert.assertNotNull(createdGoodbye.getContent());

        // Verify getAll() works well
        List<Message> allMessages = messageDao.getAll();
        Assert.assertNotNull("Check you have implemented the `getAll()` method " +
                "in the MessageDaoImpl class", allMessages);
        Assert.assertFalse(allMessages.isEmpty());
        Assert.assertEquals(2, allMessages.size());
        Assert.assertNotNull(allMessages.get(0));
        Assert.assertNotNull(allMessages.get(0).getId());
        Assert.assertEquals(1L, allMessages.get(0).getId().longValue());
        Assert.assertEquals("Welcome message", allMessages.get(0).getContent());

        // check the second message in the list
        Assert.assertNotNull(allMessages.get(1));
        Assert.assertNotNull(allMessages.get(1).getId());
        Assert.assertEquals(2L, allMessages.get(1).getId().longValue());
        Assert.assertEquals("Goodbye message", allMessages.get(1).getContent());
    }

    @Test
    public void remove_NoMessageDetails_Ok() {
        // Save message into the DB
        Message welcomeMessage = new Message();
        welcomeMessage.setContent("Welcome message");
        Message createdWelcome = messageDao.create(welcomeMessage);
        Assert.assertNotNull("Check you have implemented the `create` method " +
                "in the MessageDaoImpl class", createdWelcome);
        Assert.assertNotNull(createdWelcome.getId());
        Assert.assertNotNull(createdWelcome.getContent());

        // Verify message is in the DB
        Message created = messageDao.get(1L);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(1L, created.getId().longValue());
        Assert.assertEquals("Welcome message", created.getContent());

        // Delete message from the DB
        messageDao.remove(welcomeMessage);

        // Verify no messages are in the DB
        List<Message> allMessages = messageDao.getAll();
        Assert.assertNotNull("Check you have implemented the `getAll()` method " +
                "in the MessageDaoImpl class", allMessages);
        Assert.assertTrue("The result list should be empty. " +
                "Check the `remove(Message entity)` method was implemented " +
                "in the MessageDaoImpl class", allMessages.isEmpty());
    }

    @Test
    public void remove_WithDetails_OK() {
        // Create message
        Message message = new Message();
        message.setContent("Welcome message");
        MessageDetails details = new MessageDetails();
        details.setSender("Bob");
        message.setMessageDetails(details);
        // Verify you have implemented `create` method in the MessageDao,
        // or check the CASCADE configuration
        Message actual = messageDao.create(message);
        Assert.assertNotNull("Check you have implemented the `create` method " +
                "in the MessageDaoImpl class", actual);
        Assert.assertNotNull(actual.getId());

        // Verify message is in the DB
        Message created = messageDao.get(1L);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(1L, created.getId().longValue());
        Assert.assertEquals("Welcome message", created.getContent());

        // verify message details
        Assert.assertNotNull(actual.getMessageDetails());
        Assert.assertNotNull(actual.getMessageDetails().getId());
        Assert.assertNotNull(actual.getMessageDetails().getId());
        Assert.assertEquals(1L, actual.getMessageDetails().getId().longValue());
        Assert.assertEquals("Bob", actual.getMessageDetails().getSender());

        // Verify message details was also created
        MessageDetailsDao messageDetailsDao = new MessageDetailsDaoImpl(getSessionFactory());
        MessageDetails messageDetailsGetFromDB = messageDetailsDao.get(1L);
        Assert.assertNotNull(messageDetailsGetFromDB);
        Assert.assertNotNull(messageDetailsGetFromDB.getId());
        Assert.assertEquals(1L, messageDetailsGetFromDB.getId().longValue());
        // Delete message from the DB
        messageDao.remove(message);

        // Verify no messages are in the DB
        List<Message> allMessages = messageDao.getAll();
        Assert.assertNotNull("Check you have implemented the `getAll()` method " +
                "in the MessageDaoImpl class", allMessages);
        Assert.assertTrue("The result list should be empty. " +
                "Check the `remove(Message entity)` method was implemented " +
                "in the MessageDaoImpl class", allMessages.isEmpty());

        // Verify message details was also removed
        MessageDetails messageDetailsActual = messageDetailsDao.get(1L);
        Assert.assertNull(messageDetailsActual);
    }
}
