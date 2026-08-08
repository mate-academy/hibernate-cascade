package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.ArrayList;
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
    public User create(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add user to DB " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            Query<User> userWithComments = session.createQuery("from User u "
                    + "left join fetch u.comments "
                    + "where u.id = :id", User.class);
            userWithComments.setParameter("id", id);
            return userWithComments.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from DB by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> usersList = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query<User> usersFromDb = session.createQuery("from User u "
                    + "left join fetch u.comments", User.class);
            usersList.addAll(usersFromDb.getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Can't get users from DB", e);
        }
        return usersList;
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
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove user to DB " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
