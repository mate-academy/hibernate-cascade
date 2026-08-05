package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    private static final String GET_ALL_USERS_SQL = "FROM User";
    private Session session;
    private Transaction transaction;

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
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
            throw new RuntimeException("Could not add user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (id <= 0L) {
            throw new IllegalArgumentException("ID cannot be negative or zero");
        }
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Could not get user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            return session.createQuery(GET_ALL_USERS_SQL, User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Could not get users", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(User entity) {
        checkUser(entity);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not remove user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void checkUser(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (entity.getUsername() == null || entity.getUsername().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
    }
}
