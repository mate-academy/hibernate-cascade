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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot save new user to db", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot get user from db with id = " + id, e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users;
        try (Session session = factory.openSession()) {
            users = session.createQuery("SELECT u FROM User u", User.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot get all users from db", e);
        }
        return users;
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot remove user from db", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
