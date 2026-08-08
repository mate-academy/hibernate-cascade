package core.basesyntax.dao.impl;

import core.basesyntax.DataProcessingException;
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
        if (user == null) {
            throw new RuntimeException("unacceptable data");
        }

        if (user.getId() != null && get(user.getId()) != null) {
            throw new RuntimeException(user + "already existed in database");
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("adding " + user + " into database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public User get(Long id) {
        Session session = null;
        User user = null;
        try {
            session = factory.openSession();
            user = session.get(User.class, id);
        } catch (Exception e) {

            throw new DataProcessingException("getting user with "
                    + id + " from database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        }
    }

    @Override
    public void remove(User user) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(get(user.getId()));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("removing user: "
                    + user + " from database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
