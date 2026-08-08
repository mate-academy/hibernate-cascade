package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        return persistEntity(entity);
    }

    @Override
    public Message get(Long id) {
        return getEntity(Message.class, id);
    }

    @Override
    public List<Message> getAll() {
        return getAllEntity(Message.class);
    }

    @Override
    public void remove(Message entity) {
        deleteEntity(entity);
    }
}
