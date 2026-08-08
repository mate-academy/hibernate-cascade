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
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant save comment to db", e);
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        Session session = factory.openSession();
        return session.get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        Session session = factory.openSession();
        return session.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void remove(User entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant remove user from db", e);
        } finally {
            session.close();
        }
    }
}
