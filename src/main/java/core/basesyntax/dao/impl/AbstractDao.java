package core.basesyntax.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;
    private final Class<T> entityClass;

    protected AbstractDao(SessionFactory sessionFactory, Class<T> entityClass) {
        this.factory = sessionFactory;
        this.entityClass = entityClass;
    }

    public T create(T entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error occurred while saving entity", e);
        } finally {
            session.close();
        }
        return entity;
    }

    public T get(Long id) {
        Session session = factory.openSession();
        try {
            return session.get(entityClass, id);
        } finally {
            session.close();
        }
    }

    public List<T> getAll() {
        Session session = factory.openSession();
        try {
            return session.createQuery("FROM " + entityClass.getName(), entityClass).list();
        } finally {
            session.close();
        }
    }

    public void remove(T entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error occurred while deleting entity", e);
        } finally {
            session.close();
        }
    }
}
