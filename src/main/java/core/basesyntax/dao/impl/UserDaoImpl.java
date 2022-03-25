package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't create the user in DB. User: " + entity, e);
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
            throw new RuntimeException("Couldn't get the user from DB by id: " + id, e);
        }
        if (user == null) {
            throw new RuntimeException("There is no user in DB by such id: " + id);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            Query<User> getAllUsersQuery =
                    session.createQuery("from User", User.class);
            return getAllUsersQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Couldn't get all users from DB");
        }
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't delete the user from DB. User: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
