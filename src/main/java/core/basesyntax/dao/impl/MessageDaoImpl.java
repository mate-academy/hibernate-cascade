package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.exeptions.DataProcessingException;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
            return message;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert message", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't insert message with ID: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Query<Message> getAllMessages = session.createQuery("from Message", Message.class);
            return getAllMessages.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't show all messages", e);
        }
    }

    @Override
    public void remove(Message message) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't remove message with ID: "
                    + message.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
