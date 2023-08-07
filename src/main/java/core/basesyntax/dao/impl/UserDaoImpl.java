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
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = super.factory.openSession();
            transaction = currentSession.beginTransaction();
            currentSession.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create user: " + entity,e);
        } finally {
            currentSession.close();
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session currentSession = super.factory.openSession();) {
            User user = currentSession.get(User.class, id);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Can't get by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session currentSession = super.factory.openSession();) {
            Query<User> commentsQuery = currentSession.createQuery("from User ", User.class);
            return commentsQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get users", e);
        }
    }

    @Override
    public void remove(User entity) {
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = super.factory.openSession();
            transaction = currentSession.beginTransaction();
            currentSession.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete user: " + entity,e);
        } finally {
            currentSession.close();
        }
    }
}
