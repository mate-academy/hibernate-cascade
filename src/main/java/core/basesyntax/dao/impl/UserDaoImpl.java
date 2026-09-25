package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Comment;
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
    public User create(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            if (entity.getId() == null) {
                session.persist(entity);
            } else {
                session.merge(entity);
            }
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Could not create new "
                    + entity.getClass().getSimpleName()
                    + ", "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.createQuery(
                            "select u from User u left join fetch u.comments where u.id = :id",
                            User.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Can't get "
                    + User.class.getSimpleName()
                    + " by id: " + id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get any "
                    + User.class.getSimpleName());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            User managedUser = session.createQuery(
                            "select u from User u left join fetch u.comments where u.id = :id",
                            User.class)
                    .setParameter("id", entity.getId())
                    .uniqueResult();
            if (managedUser != null) {
                for (Comment comment : managedUser.getComments()) {
                    comment.setUserId(null);
                }
                session.remove(managedUser);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Could not remove "
                    + entity.getClass().getSimpleName()
                    + ", "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
