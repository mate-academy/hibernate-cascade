package core.basesyntax.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;
    private final Class<T> clazz;

    protected AbstractDao(SessionFactory sessionFactory, Class<T> clazz) {
        this.clazz = clazz;
        this.factory = sessionFactory;
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
            throw new RuntimeException("Can't create entity ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    public T get(Long id) {
        return (T) factory.openSession().get(clazz, id);
    }

    public List<T> getAll() {
        return factory.openSession().createQuery("FROM " + clazz.getSimpleName(), clazz).list();
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
            throw new RuntimeException("Can't delete entity ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
