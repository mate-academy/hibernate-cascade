package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        Transaction tx = null;
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            session.save(entity);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Error while creating user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        Transaction tx = null;
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            User user = session.get(User.class, id);

            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Error while getting user by id " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        Transaction tx = null;
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            List<User> users = session.createQuery("from User", User.class).getResultList();

            tx.commit();
            return users;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Error while getting all users", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void remove(User entity) {
        Transaction tx = null;
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            session.remove(entity);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Error while removing user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
