package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Comment;
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
    public User create(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert user " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            User user = session.find(User.class, id);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Can't get message by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.getResultList();
        }
    }

    @Override
    public void remove(User entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            User managed = session.find(User.class, entity.getId());

            for (Comment comment : managed.getComments()) {
                comment.setUser(null);
            }

            session.remove(managed);

            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Can't remove user " + entity, e);
        }
    }
}
