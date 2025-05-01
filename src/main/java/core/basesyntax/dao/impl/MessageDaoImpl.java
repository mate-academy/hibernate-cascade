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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot save new message to db", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Message message;
        try (Session session = factory.openSession()) {
            message = session.get(Message.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot get message from db with id = " + id, e);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages;
        try (Session session = factory.openSession()) {
            messages = session.createQuery("SELECT m FROM Message m", Message.class)
                    .getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot get all messages from db", e);
        }
        return messages;
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot remove message from db", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
