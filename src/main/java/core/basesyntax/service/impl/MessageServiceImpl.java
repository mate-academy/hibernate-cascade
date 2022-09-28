package core.basesyntax.service.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import core.basesyntax.service.MessageService;
import java.util.List;

public class MessageServiceImpl implements MessageService {
    private MessageDao dao;
    
    public MessageServiceImpl(MessageDao dao) {
        this.dao = dao;
    }
    
    @Override
    public Message create(Message entity) {
        return dao.create(entity);
    }
    
    @Override
    public Message get(Long id) {
        return dao.get(id);
    }
    
    @Override
    public List<Message> getAll() {
        return dao.getAll();
    }
    
    @Override
    public void remove(Message entity) {
        dao.remove(entity);
    }
}
