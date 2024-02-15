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
        } catch (RuntimeException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction was rollback, ",
                    exception);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public Message get(Long id) {
        Message message;
        try (Session session = factory.openSession()) {
            message = session.get(Message.class, id);
        } catch (RuntimeException exception) {
            throw new RuntimeException("There is no way to get Message by id: "
                    + id + ", ", exception);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages;
        try (Session session = factory.openSession()) {
            messages = session.createQuery("FROM Message", Message.class).getResultList();
        } catch (RuntimeException exception) {
            throw new RuntimeException("There is no way to get all messages by id, ",
                    exception);
        }
        return messages;
    }

    @Override
    public void remove(Message message) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(message);
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction was rollback, ",
                    exception);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
    }
}
