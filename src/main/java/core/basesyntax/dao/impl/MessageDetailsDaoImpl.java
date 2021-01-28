package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao<MessageDetails> implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    @Override
    public MessageDetails create(MessageDetails entity) {
        return super.create(entity);
    }
    
    @Override
    public MessageDetails get(Long id) {
        return super.get(MessageDetails.class, id);
    }
    
    @Override
    public List<MessageDetails> getAll() {
        return super.getAll(MessageDetails.class);
    }
    
    @Override
    public void remove(MessageDetails entity) {
        super.remove(entity);
    }
}
