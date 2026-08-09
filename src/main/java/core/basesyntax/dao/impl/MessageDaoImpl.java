package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
        if (entity == null) {
            throw new RuntimeException("Message is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create new entity with message in db: " + entity, ex);
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
            throw new RuntimeException("'id' is null");
        }
        Message message;
        try (Session session = factory.openSession()) {
            message = session.get(Message.class, id);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Can't get entity of message from db by id = " + id);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Message> query = builder.createQuery(Message.class);
            Root<Message> rootEntry = query.from(Message.class);
            CriteriaQuery<Message> all = query.select(rootEntry);
            messages = session.createQuery(all).getResultList();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Can't get all entities from table 'messages'");
        }
        return messages;
    }

    @Override
    public void remove(Message entity) {
        if (entity == null) {
            throw new RuntimeException("Message is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove the message from db: " + entity, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
