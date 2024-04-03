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
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't create user");
            }
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            transaction.commit();
            return user;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't get comment by id");
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            return session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Can't get all smiles");
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(User entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove comment");
        } finally {
            session.close();
        }
    }
}
