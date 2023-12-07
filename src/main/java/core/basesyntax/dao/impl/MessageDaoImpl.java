package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao<Message> implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Message.class);
    }

    @Override
    public Message create(Message message) {
        save(message);
        return message;
    }

    @Override
    public Message get(Long id) {
        return findById(id);
    }

    @Override
    public List<Message> getAll() {
        return super.getAll();
    }

    @Override
    public void remove(Message message) {
        delete(message);
    }
}
