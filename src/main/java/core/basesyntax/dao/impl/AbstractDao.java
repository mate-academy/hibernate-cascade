package core.basesyntax.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;
    protected final Class<T> clazz;

    protected AbstractDao(SessionFactory sessionFactory, Class<T> clazz) {
        this.factory = sessionFactory;
        this.clazz = clazz;
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
            throw new RuntimeException("Can't insert entity "
                    + clazz.getSimpleName() + " " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public T get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(clazz, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find entity by id " + id, e);
        }
    }

    public List<T> getAll() {
        String table = clazz.getSimpleName();
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            List<T> list = session
                    .createQuery("from " + table, clazz)
                    .list();
            tx.commit();
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Can't find elements in table "
                    + table, e);
        }
    }

    public void remove(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            if (session.contains(entity)) {
                session.remove(entity); //
            } else {
                T merged = session.merge(entity);
                session.remove(merged);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove entity " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
