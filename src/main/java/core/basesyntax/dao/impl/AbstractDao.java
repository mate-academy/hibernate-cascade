package core.basesyntax.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected <T> T createObj(T t) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(t);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create new object in DB: " + t, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return t;
    }

    protected <T> T getObj(Class<T> clazz, Long id) {
        try (Session session = factory.openSession()) {
            return session.get(clazz, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get object " + clazz, e);
        }
    }

    protected <T> List<T> getAllObj(Class<T> clazz) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from " + clazz.getSimpleName(), clazz).list();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all objects " + clazz, e);
        }
    }

    protected <T> void removeObj(T t) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(t);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove " + t, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
