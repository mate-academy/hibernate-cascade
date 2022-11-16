package core.basesyntax.dao.impl;

import core.basesyntax.dao.GenericDao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class GenericDaoImpl<T> extends AbstractDao implements GenericDao<T> {
    protected GenericDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
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
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save entity "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public abstract T get(Long id);

    @Override
    public abstract List<T> getAll();

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
            throw new RuntimeException("Can't remove entity "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
