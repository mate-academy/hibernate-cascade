package core.basesyntax.dao.impl;

import java.util.List;
import java.util.function.BiConsumer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected <T> T createEntity(T entity) {
        return perform(entity, Session::persist);
    }

    protected <T> T getEntity(Class<T> entityClass, Long id) {
        try (Session session = factory.openSession()) {
            return session.get(entityClass, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get an entity", e);
        }
    }

    protected <T> List<T> getAllEntities(Class<T> entityClass, String className) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from " + className, entityClass).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get an entity", e);
        }
    }

    protected <T> void removeEntity(T entity) {
        perform(entity, Session::remove);
    }

    private <T> T perform(T entity, BiConsumer<Session, T> action) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            action.accept(session, entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save an entity", e);
        } finally {
            session.close();
        }
        return entity;
    }
}
