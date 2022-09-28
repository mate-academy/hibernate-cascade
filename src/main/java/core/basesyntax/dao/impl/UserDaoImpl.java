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
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not save user to DB. User = " + entity, e);
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
        } catch (Exception e) {
            throw new RuntimeException("Can not find user by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            Query<User> queryGetAll = session.createQuery("from User", User.class);
            return queryGetAll.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can not get all users", e);
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
            transaction.commit();;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not delete user from DB. User " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
