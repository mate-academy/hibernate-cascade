package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Message create(Message entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't create message in DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = factory.createEntityManager();
            return entityManager.find(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message from DB by id: " + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<Message> getAll() {
        EntityManager entityManager = null;
        try {
            entityManager = factory.createEntityManager();
            return entityManager.createQuery("SELECT a FROM Message a", Message.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all messages from DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void remove(Message entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't remove message from DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
