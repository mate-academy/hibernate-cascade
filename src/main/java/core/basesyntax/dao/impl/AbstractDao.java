package core.basesyntax.dao.impl;

import core.basesyntax.exceptions.DataProcessingException;
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
            throw new DataProcessingException("Can't create entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public T get(Long id, Class<T> clazz) {
        try (Session session = factory.openSession()) {
            return session.get(clazz, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get entity", e);
        }
    }

    public List<T> getALl(Class<T> clazz) {
        try (Session session = factory.openSession()) {
            Query<T> query = session.createQuery("from " + clazz.getSimpleName(), clazz);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get list", e);
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
            throw new DataProcessingException("Can't remove entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
