package core.basesyntax.dao.impl;

import core.basesyntax.dao.GenericDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class GenericDaoImpl<T> extends AbstractDao implements GenericDao<T> {
    private final Class<?> clazz;
    private final String query;

    protected GenericDaoImpl(SessionFactory sessionFactory, Class<?> clazz, String query) {
        super(sessionFactory);
        this.clazz = clazz;
        this.query = query;
    }

    @Override
    public T create(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create " + getName(entity) + ": " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    public T get(Long id) {
        T entity;
        try (Session session = factory.openSession()) {
            //noinspection unchecked
            entity = (T) session.get(clazz, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get " + getName(clazz) + ". id=" + id, e);
        }
        return entity;
    }

    public List<T> getAll() {
        List<T> resultList;
        try (Session session = factory.openSession()) {
            //noinspection unchecked
            resultList = (List<T>) session.createQuery(query, clazz).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cannot get all " + getName(clazz), e);
        }
        return resultList;
    }

    @Override
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
            throw new RuntimeException("Cannot remove " + getName(entity) + ": " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private String getName(T entity) {
        return getName(entity.getClass());
    }

    private static String getName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }
}
