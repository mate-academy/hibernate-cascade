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
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't create message " + entity, e);
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
            entityManager = factory.openSession();
            return entityManager.find(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get message by id " + id, e);
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
            entityManager = factory.openSession();
            return entityManager.createQuery("FROM Message", Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get all messages", e);
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
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't remove message " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
