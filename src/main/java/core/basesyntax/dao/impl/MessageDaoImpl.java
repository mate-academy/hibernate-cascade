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
    public Message create(Message entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DataProcessingException("Can not create message " + entity + " into DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (EntityManager entityManager = factory.openSession()) {
            return entityManager.find(Message.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can not get message by id " + id + " from DB", e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (EntityManager entityManager = factory.openSession()) {
            Query query = entityManager.createQuery("from Message", Message.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can not get all messages ", e);
        }
    }

    @Override
    public void remove(Message entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DataProcessingException("Can not remove message " + entity + " from DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
