package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao<Message> implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        return super.create(message);
    }

    @Override
    public Message get(Long id) {
        return super.get(id, Message.class);
    }

    @Override
    public List<Message> getAll() {
        return super.getALl(Message.class);
    }

    @Override
    public void remove(Message message) {
        super.remove(message);
    }
}
