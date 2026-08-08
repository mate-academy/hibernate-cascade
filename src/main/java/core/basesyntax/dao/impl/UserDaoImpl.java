package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
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
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            if (entity.getComments() != null) {
                for (Comment comment : entity.getComments()) {
                    session.save(comment);
                }
            }
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert user entity", e);
        } finally {
            session.close();
        }
    }

    @Override
    public User get(Long id) {
        Session session = factory.openSession();
        try {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smail by id " + id, e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        Session session = factory.openSession();
        try {
            return session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get list of comments", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(User entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, entity.getId());
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete comment entity", e);
        } finally {
            session.close();
        }
    }
}
