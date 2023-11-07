package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import org.hibernate.SessionFactory;

import java.util.List;

public class MessageDaoImpl extends AbstractDao<Message> implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Message.class);
    }

    @Override
    public Message create(Message entity) {
        return super.create(entity);
    }

    @Override
    public Message get(Long id) {
        return super.get(id);
    }

    @Override
    public List<Message> getAll() {
        return super.getAll();
    }

    @Override
    public void remove(Message entity) {
        super.remove(entity);
    }
}
