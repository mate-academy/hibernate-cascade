package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        if (entity == null) {
            throw new RuntimeException("User can't be null");
        }
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
            throw new RuntimeException("Can't add user " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "SELECT u FROM User u LEFT JOIN FETCH u.comments WHERE u.id = :id",
                            User.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new RuntimeException("Can't get User by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.comments",
                    User.class).list();
        } catch (HibernateException e) {
            throw new RuntimeException("Can't get Users from DB", e);
        }
    }

    @Override
    public void remove(User entity) {
        if (entity == null) {
            throw new RuntimeException("User can't be null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            User userManaged = session.get(User.class, entity.getId());
            if (userManaged == null) {
                throw new RuntimeException("User not found");
            }
            session.remove(userManaged);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
