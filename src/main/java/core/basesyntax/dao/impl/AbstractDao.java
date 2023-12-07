package core.basesyntax.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractDao<T> {
    protected final SessionFactory sessionFactory;
    private final Class<T> entityType;

    public AbstractDao(SessionFactory sessionFactory, Class<T> entityType) {
        this.sessionFactory = sessionFactory;
        this.entityType = entityType;
    }

    public void save(T entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error while saving entity", e);
        }
    }

    public T findById(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            return session.get(entityType, id);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting entity by ID", e);
        }
    }

    public List<T> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            return session.createQuery("FROM " + entityType.getSimpleName(), entityType).list();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all entities", e);
        }
    }

    public List<T> getAll(Class<T> entityClass) {
        try (Session session = sessionFactory.getCurrentSession()) {
            return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).list();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all entities", e);
        }
    }

    public void delete(T entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting entity", e);
        }
    }
}
