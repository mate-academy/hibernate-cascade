package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    private static final String CANT_CREATE_MSG = "Can't create user entity: ";
    private static final String CANT_GET_BY_ID_MSG = "Can't get user by id: ";
    private static final String CANT_GET_ALL_MSG = "Can't get all users";
    private static final String CANT_REMOVE_MSG = "Can't remove user entity: ";
    private static final String SELECT_ALL_QUERY = "SELECT a FROM User a";

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
            throw new RuntimeException(CANT_CREATE_MSG + entity, e);
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
        } catch (Exception e) {
            throw new RuntimeException(CANT_GET_BY_ID_MSG + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(CANT_GET_ALL_MSG, e);
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
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(CANT_REMOVE_MSG + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
