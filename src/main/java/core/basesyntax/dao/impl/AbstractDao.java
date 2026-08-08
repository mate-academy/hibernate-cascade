package core.basesyntax.dao.impl;

import java.util.function.Consumer;
import java.util.function.Function;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected <T> T executeInsideTransaction(Function<Session, T> function) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            T result = function.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    protected void executeInsideTransaction(Consumer<Session> consumer) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
