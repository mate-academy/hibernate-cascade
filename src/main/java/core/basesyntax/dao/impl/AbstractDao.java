package core.basesyntax.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected T create(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create " + entity);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    protected T get(Class<T> entityClass, Long id) {
        try (Session session = factory.openSession()) {
            return session.get(entityClass, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get " + entityClass.getSimpleName() + " by id " + id);
        }
    }

    protected List<T> getAll(String getAllEntitiesQuery, Class<T> entityClazz) {
        try (Session session = factory.openSession()) {
            Query<T> query = session.createQuery(getAllEntitiesQuery, entityClazz);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Can't get all" + entityClazz.getSimpleName() + " from DB", e);
        }
    }

    protected void remove(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove " + entity + " from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
