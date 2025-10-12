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
    public Message create(Message message) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't create message " + message + " ,transaction rollback.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            Message message = session.find(Message.class, id);
            if (message != null) {
                return message;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    "Can't get message with id " + id + " from database", e);
        }
        return null;
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            List<Message> messagesFromDb = session.createQuery("FROM Message ", Message.class).list();
            if (messagesFromDb != null) {
                return messagesFromDb;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    "Can't get list of messages from database", e);
        }
        return null;
    }

    @Override
    public void remove(Message message) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(message);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Transaction with message " + message + " rolling back.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
