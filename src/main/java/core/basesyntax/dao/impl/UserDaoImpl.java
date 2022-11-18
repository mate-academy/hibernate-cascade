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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add to db user: "
                    + entity.getUsername(), e);
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
        Session session = null;
        try {
            session = factory.openSession();
            user = session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from db: "
                    + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = null;
        List<User> users;
        try {
            session = factory.openSession();
            Query<User> fromUser = session.createQuery("From User", User.class);
            users = fromUser.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get users from db", e);
        } finally {
            if (session != null) {
                session.close();
            }
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
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete user from db: "
                    + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
