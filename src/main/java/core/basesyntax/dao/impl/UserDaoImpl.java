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
    public User create(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't create user " + user + ", transaction rollback.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            User user = session.find(User.class, id);
            if (user != null) {
                return user;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    "Can't get user with id " + id + " from database", e);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            List<User> usersFromDb = session.createQuery("FROM User ", User.class).list();
            if (usersFromDb != null) {
                return usersFromDb;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    "Can't get list of users from database", e);
        }
        return null;
    }

    @Override
    public void remove(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't remove user " + user + " , transaction rollback.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
