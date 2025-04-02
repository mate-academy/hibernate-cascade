package core.basesyntax.service.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.lib.Inject;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.service.MessageDetailsService;

public class MessageDetailsServiceImpl implements MessageDetailsService {
    @Inject
    private MessageDetailsDao messageDetailsDao;

    @Override
    public MessageDetails create(MessageDetails entity) {
        return messageDetailsDao.create(entity);
    }

    @Override
    public MessageDetails get(Long id) {
        MessageDetails messageDetails = messageDetailsDao.get(id);
        if (messageDetails == null) {
            throw new DataProcessingException("Comment not found for id: " + id);
        }
        return messageDetails;
    }
}
