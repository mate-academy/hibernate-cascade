package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create the User. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(User.class, id);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get a User. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        Session session = null;
        List<User> results;
        try {
            session = factory.openSession();
            results = session.createQuery("SELECT a FROM User a", User.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get all Users. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return results;
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create the comment. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
