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
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create User: " + entity.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entity;
    }

    @Override
    public User get(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Cannot get User: id is null");
        }

        Session session = null;
        User entity;

        try {
            session = factory.openSession();
            entity = (User) session.get(User.class, id);
            if (entity == null) {
                throw new RuntimeException("User with id " + id + " not exists");
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot get User: " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public List<User> getAll() {
        List<User> entityList = null;
        Session session = null;

        try {
            session = factory.openSession();
            entityList = session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cannot get User: " + e, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entityList;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot remove User: " + entity.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
