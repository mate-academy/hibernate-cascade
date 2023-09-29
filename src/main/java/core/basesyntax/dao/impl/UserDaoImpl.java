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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create User: " + user, e);
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
            Query<User> getUserQuery = session.createQuery("from User u left join fetch u.comments "
                    + "where u.id = :id", User.class);
            getUserQuery.setParameter("id", id);
            return getUserQuery.getSingleResult();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get from DB user by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            Query<User> allUsersList = session.createQuery("from User", User.class);
            return allUsersList.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get from DB list of all users", e);
        }
    }

    @Override
    public void remove(User user) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't delete user from DB. User: " + user, e);
        }
    }
}
