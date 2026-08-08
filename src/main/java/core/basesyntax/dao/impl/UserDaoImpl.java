package core.basesyntax.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;

public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public User create(User user) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't create user", e);
        }
    }
    @Override
    public User get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM User u LEFT JOIN FETCH u.comments WHERE u.id = :id",
                    User.class
            ).setParameter("id", id).uniqueResult();
        }
    }
    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }
    @Override
    public void remove(User user) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.remove(session.contains(user) ? user : session.merge(user));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't remove user", e);
        }
    }
}

