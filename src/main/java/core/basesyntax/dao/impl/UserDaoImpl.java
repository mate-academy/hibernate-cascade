package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User user) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save user in DB", e);
        }
        return user;
    }

    @Override
    public User get(Long id) {
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user with id=" + id + " from DB", e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            Query<User> getAllUsersQuery = session.createQuery(
                    "from User", User.class);
            return getAllUsersQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users from DB", e);
        }
    }

    @Override
    public void remove(User user) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete user from DB", e);
        }
    }
}
