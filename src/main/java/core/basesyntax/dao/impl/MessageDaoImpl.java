package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add message: " + entity + " to database", ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = factory.createEntityManager();
            return entityManager.find(Message.class, id);
        } catch (Exception ex) {
            throw new RuntimeException("Can't get message by id: " + id + " from database", ex);
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
            return entityManager.createQuery("SELECT m FROM Message m", Message.class)
                    .getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Can't get all messages");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void remove(Message entity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove message: " + entity + " from database");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
