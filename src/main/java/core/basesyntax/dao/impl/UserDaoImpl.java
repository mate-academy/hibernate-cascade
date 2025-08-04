package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Comment;
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
            throw new DataProcessingException("Failed to create User " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {

        Session session = factory.openSession();

        try {
            User user = session.find(User.class, id);
            return user;
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get user by id " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public List<User> getAll() {
        Session session = factory.openSession();

        List<User> users = session
                .createQuery("FROM User" , User.class)
                .getResultList();

        session.close();

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

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to remove User " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
