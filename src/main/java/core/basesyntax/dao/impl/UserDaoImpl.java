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
    public User create(User user) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Can't create new user " + user, e);
        } finally {
            session.close();
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find user with id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM user";
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(sql, User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users ", e);
        }
    }

    @Override
    public void remove(User user) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Can't remove user " + user, e);
        } finally {
            session.close();
        }
    }
}
