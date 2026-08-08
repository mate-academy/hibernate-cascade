package core.basesyntax.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;
    private final Class<T> entityType;

    protected AbstractDao(SessionFactory sessionFactory, Class<T> entityType) {
        this.factory = sessionFactory;
        this.entityType = entityType;
    }

    public T create(T entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert "
                    + entityType.getSimpleName() + " into DB: " + entity, e);
        } finally {
            session.close();
        }
    }

    public T get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(entityType, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get "
                    + entityType.getSimpleName() + " from DB." + e.getMessage());
        }
    }

    public List<T> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "select distinct e from " + entityType.getSimpleName() + " e",
                    entityType).list();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all "
                    + entityType.getSimpleName() + " from DB." + e.getMessage());
        }
    }

    public void remove(T entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            session.remove(session.merge(entity));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove "
                    + entityType.getSimpleName() + " from DB." + e.getMessage());
        } finally {
            session.close();
        }
    }
}
