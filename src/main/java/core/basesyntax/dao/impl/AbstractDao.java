package core.basesyntax.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected T save(T entity) {
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
            throw new RuntimeException("Could not save entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    protected T findById(Class<T> clazz, Long id) {
        try (Session session = factory.openSession()) {
            return session.get(clazz, id);
        }
    }

    protected List<T> findAll(Class<T> clazz) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from " + clazz.getSimpleName(), clazz).list();
        }
    }

    protected void delete(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not delete entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
