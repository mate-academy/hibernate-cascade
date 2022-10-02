package core.basesyntax.dao.impl;

import core.basesyntax.dao.GenericDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class GenericDaoImpl<T> extends AbstractDao<T> implements GenericDao<T> {
    protected GenericDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

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
            throw new RuntimeException("Can not remove from DB the comment " + entity);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
