package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
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
            throw new RuntimeException("Can't create User in DB" + entity.getId());
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from DB" + id);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(QUERY_GET_ALL_USERS, User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("can't get all users from DB", e);
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
                transaction.rollback();
                throw new RuntimeException("Can't delete User from DB" + entity.getId());
            }
        }
    }
}
