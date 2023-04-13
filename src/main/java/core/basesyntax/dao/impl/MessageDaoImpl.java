package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't save message to DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return entity;
    }

    @Override
    public Message get(Long id) {
        Message message;

        EntityManager entityManager = null;

        try {
            entityManager = factory.createEntityManager();
            message = entityManager.find(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve message from DB with id: " + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return message;
    }

    @Override
    public List<Message> getAll() {
        EntityManager entityManager = null;
        List<Message> list = new ArrayList<>();

        try {
            entityManager = factory.createEntityManager();
            String hql = "FROM Message";
            Query query = entityManager.createQuery(hql);
            list.addAll((List<Message>) query.getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Could not get all messages from DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return list;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't remove message from DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
