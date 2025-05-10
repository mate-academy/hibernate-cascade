package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to save a message: " + message, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return message;
    }

    @Override
    public Message get(Long id) {
        try (EntityManager entityManager = factory.openSession()) {
            return entityManager.find(Message.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get a message with id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (EntityManager entityManager = factory.openSession()) {
            Query query = entityManager.createQuery("from Message", Message.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get all messages", e);
        }
    }

    @Override
    public void remove(Message message) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to remove a message: " + message, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
