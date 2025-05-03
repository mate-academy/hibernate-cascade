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
            throw new RuntimeException("Can not save User object with data: "
                    + entity + " to Db", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "select u from User u "
                                    + "left join fetch u.comments "
                                    + "where u.id = :id", User.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Can not get User object with id: "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "select distinct u from User u "
                                    + "left join fetch u.comments", User.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can not get list of User objects from DB", e);
        }
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            User managedUser = session.createQuery(
                            "select u from User u "
                                    + "left join fetch u.comments "
                                    + "where u.id = :id", User.class)
                    .setParameter("id", entity.getId())
                    .uniqueResult();
            if (managedUser != null) {
                session.remove(managedUser);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not remove User object with data: "
                    + entity + " from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
