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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't add a message "
                    + message.getContent() + " to DB", e);
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
            Message message = session.get(Message.class, id);
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get a message from DB by id = " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            List<Message> messages =
                    session.createQuery("from Message", Message.class).getResultList();
            return messages;
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get all messages from DB", e);
        }
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't delete a message "
                    + message.getContent() + " from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
