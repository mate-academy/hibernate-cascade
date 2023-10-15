package core.basesyntax.dao.impl;

import core.basesyntax.dao.DataProcessingException;
import core.basesyntax.model.Model;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public abstract class AbstractDao {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected Model createEntity(Model entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new DataProcessingException("Can't save "
                    + entity.getClass().getName() + ": " + entity, e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    protected Model getEntity(Long id, Class<? extends Model> entityClass) {
        Model entity;
        try (Session session = factory.openSession()) {
            entity = session.get(entityClass, id);
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get "
                    + entityClass.getName() + " with id: " + id, e);
        }
        return entity;
    }

    protected List<? extends Model> getAllEntities(Class<? extends Model> entityClass) {
        try (Session session = factory.openSession()) {
            Query<? extends Model> query = session.createQuery("FROM "
                    + entityClass.getName(), entityClass);
            return query.list();
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get all entities of "
                    + entityClass.getName(), e);
        }
    }

    protected void removeEntity(Model entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't remove "
                    + entity.getClass().getName() + ": " + entity, e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            if (session != null) {
                session.close();
            }
        }
    }
}
