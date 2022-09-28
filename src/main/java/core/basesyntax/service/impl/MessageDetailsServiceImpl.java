package core.basesyntax.service.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.service.MessageDetailsService;

public class MessageDetailsServiceImpl implements MessageDetailsService {
    private MessageDetailsDao dao;
    
    public MessageDetailsServiceImpl(MessageDetailsDao dao) {
        this.dao = dao;
    }
    
    @Override
    public MessageDetails create(MessageDetails entity) {
        return dao.create(entity);
    }
    
    @Override
    public MessageDetails get(Long id) {
        return dao.get(id);
    }
}
