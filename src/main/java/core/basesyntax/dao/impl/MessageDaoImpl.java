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
        try {
            return factory.fromTransaction(session -> {
                session.persist(entity);
                return entity;
            });
        } catch (Exception e) {
            throw new RuntimeException("Can't create Message: " + entity, e);
        }
    }

    @Override
    public Message get(Long id) {
        try {
            return factory.fromSession(session -> session.get(Message.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can't get Message by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try {
            return factory.fromSession(session -> session.createQuery("FROM Message", Message.class)
                    .getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Can't get List of Messages from db", e);
        }
    }

    @Override
    public void remove(Message entity) {
        try {
            factory.inTransaction(session -> session.remove(entity));
        } catch (Exception e) {
            throw new RuntimeException("Can't remove Message: " + entity, e);
        }
    }
}
