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
        Transaction transaction = null;
        Session session = null;
        try {
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Content entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        Transaction transaction = null;
        try (Session session = super.factory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't get entity from DB", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = super.factory.openSession()) {
            Query<User> userQuery = session.createQuery("from User", User.class);
            return userQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get entities from DB", e);
        }
    }

    @Override
    public void remove(User entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
