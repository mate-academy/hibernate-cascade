package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can not get message by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        Query<Message> messageQuery = factory
                .openSession().createQuery("from Message", Message.class);
        return messageQuery.getResultList();
    }

    @Override
    public void remove(Message entity) {
        factory.inTransaction(session -> session.remove(entity));
    }
}
