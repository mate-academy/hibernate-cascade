package core.basesyntax.service.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import core.basesyntax.service.MessageService;
import jakarta.transaction.Transactional;

public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;

    public MessageServiceImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    @Transactional
    public void remove(Message message) {
        messageDao.remove(message);
    }
}
