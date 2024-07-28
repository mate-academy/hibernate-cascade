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

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add User object in database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        User user = null;

        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve User object by id:" + id, e);
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = null;

        try (Session session = factory.openSession()) {
            userList = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve all User objects:", e);
        }

        return userList;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove User object from database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
