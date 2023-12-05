package core.basesyntax.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    public void save(T entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error while saving entity", e);
        }
    }

    public T findById(Class<T> clazz, Long id) {
        try (Session session = factory.openSession()) {
            return session.get(clazz, id);
        } catch (Exception e) {
            throw new RuntimeException("Error while finding entity by id", e);
        }
    }

    public void update(T entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error while updating entity", e);
        }
    }

    public void delete(T entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting entity", e);
        }
    }
}
