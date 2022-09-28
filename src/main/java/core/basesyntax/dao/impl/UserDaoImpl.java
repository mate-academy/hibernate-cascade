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
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't save user to DB " + entity, exception);
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
            return session.get(User.class, id);
        } catch (Exception exception) {
            throw new RuntimeException("Can`t get user " + id, exception);
        }
    }

    @Override
    public List<User> getAll() {
        String hqlQuery = "FROM User";
        try (Session session = factory.openSession()) {
            return session.createQuery(hqlQuery, User.class).getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Can't get all comments from DB", exception);
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
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.commit();
            }
            throw new RuntimeException("Can't remove user " + entity, exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
