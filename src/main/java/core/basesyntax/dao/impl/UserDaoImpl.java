package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.User;
import java.util.List;
import java.util.function.Consumer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        executeInTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("User not found with id: " + id);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users;
        try (Session session = factory.openSession()) {
            users = session.createQuery("from User").list();
            return users;
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Users was not found. " + e.getMessage());
        }
    }

    @Override
    public void remove(User entity) {
        executeInTransaction(session -> session.remove(entity));
    }

    private void executeInTransaction(Consumer<Session> action) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Operation with comment not done. " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
