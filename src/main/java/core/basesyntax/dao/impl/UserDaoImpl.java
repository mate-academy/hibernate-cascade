package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
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
            throw new RuntimeException("Was not able to create a new user.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        User user = null;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Was not able to get a user by ID " 
                    + id + " from db.", e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> comments = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query<User> getAllQuery = 
                    session.createQuery("from User", User.class);
            comments = getAllQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Was not able to get all users from db.", e);
        }
        return comments;
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
            throw new RuntimeException("Was not able to delete a user by ID " 
                    + entity.getId() + " from db.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
