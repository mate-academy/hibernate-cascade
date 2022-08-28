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
            session.persist(entity);
            return entity;
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create User " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get User with id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "FROM User";
        try (Session session = factory.openSession()) {
            Query<User> getAllUsersQuery = session.createQuery(query, User.class);
            return getAllUsersQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all Users", e);
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove User " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
