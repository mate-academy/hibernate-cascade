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

    public T create(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create entity: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public T get(Class<T> clazz, Long id) {
        try (Session session = factory.openSession()) {
            return session.get(clazz, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get entity by id: " + id, e);
        }
    }

    public List<T> getAll(Class<T> clazz) {
        try (Session session = factory.openSession()) {
            String query = "from " + clazz.getName();
            return session.createQuery(query, clazz).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all entities of type: "
                    + clazz.getName(), e);
        }
    }

    public void remove(T entity) {
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
            throw new RuntimeException("Failed to remove entity: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
