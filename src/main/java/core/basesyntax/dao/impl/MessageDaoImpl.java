package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
            throw new RuntimeException("Can't insert Message to DB: " + message);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public Message get(Long id) {
        Message dbMessage = null;
        try (Session session = factory.openSession()) {
            dbMessage = session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Message by id: " + id);
        }
        return dbMessage;
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        List<Message> messages;
        try {
            session = factory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Message> criteria = builder.createQuery(Message.class);
            criteria.from(Message.class);
            messages = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all Messages from DB. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant delete Message from DB: " + message, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
