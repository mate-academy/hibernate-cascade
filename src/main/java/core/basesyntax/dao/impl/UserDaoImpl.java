package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import javax.persistence.Query;
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

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add " + entity, e);
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
            User entity = session.get(User.class, id);
            return entity;
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get " + User.class + " by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "FROM User";
        try (Session session = factory.openSession()) {
            Query getAllQuery = session.createQuery(query, User.class);
            return getAllQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all users.", e);
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

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
