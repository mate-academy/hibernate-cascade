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
            session.saveOrUpdate(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save user to database " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession();) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from db by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession();) {
            Query<User> getAllUsersQuery
                    = session.createQuery("from User");
            return getAllUsersQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users from db");
        }
    }

    @Override
    public void remove(User user) {
        try {
            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Cant remove user from database " + user, e);
        }
    }
}
