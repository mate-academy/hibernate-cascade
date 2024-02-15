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
        } catch (RuntimeException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction was rollback, ",
                    exception);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public User get(Long id) {
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
        } catch (RuntimeException exception) {
            throw new RuntimeException("There is no way to get User by id: "
                    + id + ", ", exception);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users;
        try (Session session = factory.openSession()) {
            users = session.createQuery("FROM User", User.class).getResultList();
        } catch (RuntimeException exception) {
            throw new RuntimeException("There is no way to get all users, ",
                    exception);
        }
        return users;
    }

    @Override
    public void remove(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction was rollback, ",
                    exception);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
    }
}
