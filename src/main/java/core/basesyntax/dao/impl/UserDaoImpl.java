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
    public User create(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t create user:" + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(User.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can`t get user by id:" + id, e);
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT u FROM User u";
        Session session = null;
        try {
            session = factory.openSession();
            Query<User> users = session.createQuery(query, User.class);
            return users.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can`t get all comments from db");
        } finally {
            assert session != null;
            session.close();
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t remove user:" + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
