package core.basesyntax.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public abstract class AbstractDao {
    private static final String CREATE_ERROR_MESSAGE = "Can't create ";
    private static final String REMOVE_ERROR_MESSAGE = "Can't create ";
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected <T> T createEntity(T entity) {
        return executeCrudOperation(entity, true);
    }

    protected <T> T getEntity(Class<T> entityType, Long id) {
        try (Session session = factory.openSession()) {
            return session.get(entityType, id);
        }
    }

    protected <T> List<T> getAllEntities(Class<T> entityType) {
        try (Session session = factory.openSession()) {
            Query<T> allEntities = session.createQuery("from "
                    + entityType.getSimpleName(), entityType);
            return allEntities.getResultList();
        }
    }

    protected <T> void removeEntity(T entity) {
        executeCrudOperation(entity, false);
    }

    private <T> T executeCrudOperation(T entity, boolean createOrNot) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            if (createOrNot) {
                session.persist(entity);
            } else {
                session.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    (createOrNot ? CREATE_ERROR_MESSAGE : REMOVE_ERROR_MESSAGE)
                    + entity.getClass().toString()
                    + ": " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }
}
