package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        Transaction transaction = null;
        Session session;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save entity to DB", e);
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        }
    }

    @Override
        public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("Select c From Message c",
                    Message.class).getResultList();
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Message message = session.get(Message.class, entity.getId());
        session.remove(message);
        transaction.commit();
        session.close();
    }
}
