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
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t save entity:" + entity,e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null!");
        }
        try (Session session = factory.openSession()) {
            Message result = session.get(Message.class, id);
            if (result != null) {
                return result;
            }
            throw new IllegalArgumentException("ID cannot be null!");
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            List<Message> messages = session.createQuery("from Message").list();
            return messages;
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t delete entity: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
