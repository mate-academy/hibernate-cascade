package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.ArrayList;
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
    public User create(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create user entity " + entity, e);
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
        } catch (Exception e) {
            throw new RuntimeException("Can't get user by id " + id, e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query<User> getAllUsers = session.createQuery("FROM User", User.class);
            allUsers = getAllUsers.getResultList();
            return allUsers;
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users ", e);
        }
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
            throw new RuntimeException("Can't remove user entity " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
