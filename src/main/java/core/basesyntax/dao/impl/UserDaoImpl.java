package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    private static final String EXCEPTION_CAN_T_CREATE_USER_MESSAGE =
            "Can not to create user with id: ";
    private static final String EXCEPTION_CAN_T_GET_USER_MESSAGE =
            "Can not to get user with id: ";
    private static final String EXCEPTION_CAN_T_GET_ALL_USERS_MESSAGE =
            "Can not to get all users";
    private static final String EXCEPTION_CAN_T_REMOVE_USER_MESSAGE =
            "Can not to remove user with id: ";
    private static final String QUERY_GET_ALL_USERS = "SELECT * FROM users";

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                return entity;
            } catch (Exception e) {
                transaction.rollback();
            }
            throw new RuntimeException(EXCEPTION_CAN_T_CREATE_USER_MESSAGE + entity.getId());
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException(EXCEPTION_CAN_T_GET_USER_MESSAGE + id);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(QUERY_GET_ALL_USERS, User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(EXCEPTION_CAN_T_GET_ALL_USERS_MESSAGE, e);
        }
    }

    @Override
    public void remove(User entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.remove(entity);
                transaction.commit();
            } catch (Exception e) {
                throw new RuntimeException(EXCEPTION_CAN_T_REMOVE_USER_MESSAGE + entity.getId(), e);
            }
        }
    }
}
