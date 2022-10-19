package core.basesyntax.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    public void transactionRollBack(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    public void sessionClose(Session session) {
        if (session != null) {
            session.close();
        }
    }
}
