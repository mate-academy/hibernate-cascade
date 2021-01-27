package core.basesyntax.dao.impl;

import core.basesyntax.dao.GenericDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public abstract class AbstractDao<T> implements GenericDao<T> {
    private final Class aClass;
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
        aClass = initClass();
    }
    
    private Class initClass() {
        try {
            String classPath = this.getClass().getGenericSuperclass().getTypeName();
            int from = classPath.indexOf('<') + 1;
            int to = classPath.indexOf('>');
            return Class.forName(classPath.substring(from, to));
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
    public T get(Long id) {
        try (Session session = factory.openSession()) {
            return (T) session.get(aClass, id);
        } catch (Exception e) {
            throw new RuntimeException("Errored while retrieving data by id "
                                       + id + " from DB", e);
        }
    }
    
    @Override
    public List<T> getAll() {
        try (Session session = factory.openSession()) {
            Query<T> allUsers = session.createQuery("from " + aClass.getSimpleName(), aClass);
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
