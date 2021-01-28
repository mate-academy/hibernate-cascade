package core.basesyntax.dao.impl;

import core.basesyntax.dao.GenericDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;
    private final Class<T> clazz;
    
    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
        clazz = initClass();
    }
    
    private Class<T> initClass() {
        try {
            String classPath = this.getClass().getGenericSuperclass().getTypeName();
            int from = classPath.indexOf('<') + 1;
            int to = classPath.indexOf('>');
            return (Class<T>) Class.forName(classPath.substring(from, to));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Errored while initialization " + this.getClass());
        }
    }
    
    @Override
    public T create(T entity) {
        Transaction transaction = null;
        Session session = null;
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
            throw new RuntimeException("Errored while adding " + entity + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public T get(Class<T> clazz, Long id) {
        try (Session session = factory.openSession()) {
            return session.get(clazz, id);
        } catch (Exception e) {
            throw new RuntimeException("Errored while retrieving data by id "
                                       + id + " from DB", e);
        }
    }
    
    @Override
    public List<T> getAll(Class<T> clazz) {
        try (Session session = factory.openSession()) {
            Query<T> allUsers = session.createQuery("from " + clazz.getSimpleName(), clazz);
            return allUsers.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Errored while retrieving all data from DB");
        }
    }
    
    @Override
    public void remove(T entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Errored while deleting data "
                                       + entity + " from DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
