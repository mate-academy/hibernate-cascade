package core.basesyntax.service.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.lib.Inject;
import core.basesyntax.model.Message;
import core.basesyntax.service.MessageService;
import java.util.List;

public class MessageServiceImpl implements MessageService {
    @Inject
    private MessageDao messageDao;

    @Override
    public Message create(Message entity) {
        return messageDao.create(entity);
    }

    @Override
    public Message get(Long id) {
        Message message = messageDao.get(id);
        if (message == null) {
            throw new DataProcessingException("Message not found for id: " + id);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = messageDao.getAll();
        if (messages == null) {
            throw new DataProcessingException("Message not found for id's");
        }
        return messages;
    }

    @Override
    public void remove(Message entity) {
        messageDao.remove(entity);
    }
}
