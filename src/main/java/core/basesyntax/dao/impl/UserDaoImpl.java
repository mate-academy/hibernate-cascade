package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    private Session session;
    private Transaction transaction;

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User user) {
        checkUser(user);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t add User to database!" + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve "
                    + "User from the database with id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve users "
                    + "from the database", e);
        }
    }

    @Override
    public void remove(User user) {
        checkUser(user);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant remove User!" + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null! ");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new RuntimeException("User name cannot be empty or null! " + user);
        }
    }
}
