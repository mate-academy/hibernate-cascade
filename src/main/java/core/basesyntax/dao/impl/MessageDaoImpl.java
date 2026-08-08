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
        factory.inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public Message get(Long id) {
        return factory.fromSession(session -> session.get(Message.class, id));
    }

    @Override
    public List<Message> getAll() {
        return factory.fromSession(
                session -> session.createQuery("from Message ", Message.class)
                        .getResultList());
    }

    @Override
    public void remove(Message entity) {
        factory.inTransaction(session -> session.remove(entity));
    }
}
