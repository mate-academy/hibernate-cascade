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
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create " + entity.getClass().getSimpleName(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery(
                            "SELECT u FROM User u "
                                    + "LEFT JOIN FETCH u.comments "
                                    + "WHERE u.id = :id",
                            User.class)
                    .setParameter("id", id)
                    .uniqueResult();

            if (user == null) {
                throw new RuntimeException("User not found with id " + id);
            }

            return user;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Can't find user by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        Session session = null;
        Transaction transaction = null;
        List<User> userList = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            userList = session.createQuery("FROM User", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all elements from DB ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userList;
    }

    @Override
    public void remove(User entity) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, entity.getId());
            if (user != null) {
                session.remove(user);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove user " + entity, e);
        }
    }
}
