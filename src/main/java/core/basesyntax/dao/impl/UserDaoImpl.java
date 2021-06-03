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
    public User create(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create user: " + user, e);
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
            throw new RuntimeException("Can't get user from DB with this id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            Query<User> getAllCommentQuery = session
                    .createQuery("select c from users c", User.class);
            return getAllCommentQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users:", e);
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
            throw new RuntimeException("Can't create user: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
