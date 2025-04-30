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

            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create user: " + entity, e);
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
            Query<User> query = session.createQuery(
                    "FROM User u LEFT JOIN FETCH u.comments WHERE u.id = :id", User.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Can't get user: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {

            Query<User> query = session.createQuery(
                    "FROM User u LEFT JOIN FETCH u.comments", User.class);
            return query.list();

        } catch (Exception e) {
            throw new RuntimeException("Can't get users", e);
        }
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, entity.getId());
            if (user != null) {
                user.getComments().clear();
                session.merge(user);
            }
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove user: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
