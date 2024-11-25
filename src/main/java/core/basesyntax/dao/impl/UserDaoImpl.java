package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.exception.DataAccessException;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
            entity.setId((Long) session.getIdentifier(entity));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException("Can't add user to DB. User: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        Session session = factory.openSession();
        User user = session.createQuery("from User u "
                        + "left join fetch u.comments "
                        + "where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = factory.openSession();
        List<User> users = session.createQuery("from User u "
                                + "left join fetch u.comments",
                        User.class)
                .getResultList();
        session.close();
        return users;
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException("Can't remove user from DB. User: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
