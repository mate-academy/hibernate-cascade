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
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Unable to save " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    protected T findById(Class<T> clazz, Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(clazz, id);
        } catch (Exception e) {
            throw new RuntimeException("Cant find by id: " + id + e);
        }
    }

    protected List<T> findAll(Class<T> clazz) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from " + clazz.getSimpleName(), clazz).list();
        }
    }

    protected void delete(T entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Unable to delete " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
