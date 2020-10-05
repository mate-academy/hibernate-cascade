package core.basesyntax.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    public void remove(T entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete "
                    + entity + " from the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
