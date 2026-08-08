package core.basesyntax.dao.impl;

import core.basesyntax.DataProcessingException;
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
        if (message == null) {
            throw new RuntimeException("unacceptable data");
        }

        if (message.getId() != null && get(message.getId()) != null) {
            throw new RuntimeException(message + "already existed in database");
        }

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
            throw new DataProcessingException("adding " + message + " into database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        Message message = null;
        try {
            session = factory.openSession();
            message = session.get(Message.class, id);
        } catch (Exception e) {

            throw new DataProcessingException("getting message with "
                    + id + " from database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Message", Message.class).getResultList();
        }
    }

    @Override
    public void remove(Message message) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(get(message.getId()));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("removing message: "
                    + message + " from database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
