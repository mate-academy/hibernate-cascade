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
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            try {
                transaction = session.beginTransaction();
                session.persist(entity);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("can't add movie data: " + entity, e);
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM User ", User.class).list();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void remove(User entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            try {
                transaction = session.beginTransaction();
                session.remove(entity);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException();
            }
        }
    }
}
