package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {

    public static final String ERROR_DURING_CREATION_OF_USER =
            "Error during creation of user -> %s";
    public static final String NO_USER_WITH_SUCH_ID =
            "No user with such id -> %d";
    public static final String ERROR_DURING_RETRIEVING_USER_WITH_ID =
            "Error during retrieving user with id -> %d";
    public static final String ERROR_DURING_RETRIEVING_ALL_USERS =
            "Error during retrieving all users.";
    public static final String ERROR_DURING_DELETING_USER =
            "Error during deleting user -> %s";

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
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    ERROR_DURING_CREATION_OF_USER.formatted(entity), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            Optional<User> userFromDB = Optional.ofNullable(session.get(User.class, id));
            return userFromDB.orElseThrow(
                    () -> new EntityNotFoundException(NO_USER_WITH_SUCH_ID.formatted(id)));
        } catch (Exception e) {
            throw new DataProcessingException(
                    ERROR_DURING_RETRIEVING_USER_WITH_ID.formatted(id), e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT u FROM User u", User.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(ERROR_DURING_RETRIEVING_ALL_USERS, e);
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
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException(ERROR_DURING_DELETING_USER.formatted(entity),
                    e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
