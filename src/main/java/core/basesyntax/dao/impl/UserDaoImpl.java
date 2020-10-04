package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Log4j
public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                log.debug("Transaction creation for " + entity.toString()
                        + " has been rollbacked.", e);
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Content entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        log.debug("Entity " + entity.toString() + " created");
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Can't get entity", e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query<User> getAllCommentQuery = session.createQuery("from User ", User.class);
            userList = getAllCommentQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Couldn't get all comments", e);
        }
        return userList;
    }

    @Override
    public void remove(User entity) {
        Transaction transaction;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            User user = session.find(User.class, entity.getId());
            if (user != null) {
                session.remove(user);
                transaction.commit();
            }
        } catch (HibernateException e) {
            throw new RuntimeException("User " + entity.getId() + " not deleted", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        log.debug("User " + entity.toString() + " has been removed");
    }
}
