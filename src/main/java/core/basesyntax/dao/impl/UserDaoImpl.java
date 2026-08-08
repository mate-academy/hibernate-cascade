package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import java.util.Objects;
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
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            Objects.requireNonNull(transaction).rollback();
            throw new RuntimeException("Can't add user to db", e);
        } finally {
            Objects.requireNonNull(session).close();
        }
        return entity;
    }

    @Override
    public User get(Long id) {

        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from db by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {

        Session session = null;
        try {
            session = factory.openSession();
            return session.createQuery("SELECT a FROM User a", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users from db", e);
        } finally {
            Objects.requireNonNull(session).close();
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
        } catch (Exception e) {
            Objects.requireNonNull(transaction).rollback();
            throw new RuntimeException("Can't delete user from db", e);
        } finally {
            Objects.requireNonNull(session).close();
        }
    }
}
