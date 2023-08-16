package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Can't add user "
                    + user.getUsername() + " to DB");
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
            return session.get(User.class, id);
        } catch (Throwable e) {
            throw new RuntimeException("Can't get user by provided id " + id);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            Query<User> getAllFromUsersQuery
                    = session.createQuery("FROM User", User.class);
            return getAllFromUsersQuery.getResultList();
        } catch (Throwable e) {
            throw new RuntimeException("Can't get all users");
        }
    }

    @Override
    public void remove(User user) {
        Transaction transaction;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Throwable e) {
            throw new RuntimeException("Can't remove user " + user.getUsername());
        }
    }
}
