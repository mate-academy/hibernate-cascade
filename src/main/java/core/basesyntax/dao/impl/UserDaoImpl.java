package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import java.util.Optional;
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
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(String.format(
                    "An error occurred while trying to add a user %s to the database",
                    entity), ex);
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(User.class, id))
                    .orElseThrow(() -> new RuntimeException(String.format(
                    "An error occurred while trying to retrieve a user "
                            + "with ID %d from the database", id)));
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM User a", User.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while trying to retrieve all users "
                    + "from the database", ex);
        }
    }

    @Override
    public void remove(User user) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(String.format("An error occurred while trying to remove "
                    + "a user %s from the database", user), ex);
        }
    }
}
