package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected T create(T comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();
            return comment;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Content entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void remove(T item) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(item);
            transaction.commit();
        }
    }
}
