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
        SessionFactory factory = super.factory;
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
            throw new RuntimeException("can't create user", e);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        SessionFactory factory = super.factory;

        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user with id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        SessionFactory factory = super.factory;
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT user FROM User user", User.class)
                    .getResultList();

        } catch (Exception e) {
            throw new RuntimeException("can't get all users", e);
        }
    }

    @Override
    public void remove(User entity) {
        SessionFactory factory = super.factory;
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
            throw new RuntimeException("Cannot remove user with id: " + entity.getId(), e);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
