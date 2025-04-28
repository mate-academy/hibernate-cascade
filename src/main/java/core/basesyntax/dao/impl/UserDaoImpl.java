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
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t save entity:" + entity,e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            User result = session.get(User.class, id);
            if (result != null) {
                return result;
            }
            throw new RuntimeException("Cannot find User by id " + id);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            List<User> users = session.createQuery("from User").list();
            return users;
        }
    }

    @Override
    public void remove(User entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t delete entity: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
