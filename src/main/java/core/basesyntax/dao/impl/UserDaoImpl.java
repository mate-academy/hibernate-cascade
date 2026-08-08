package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.User;
import jakarta.persistence.Table;
import java.util.List;
import org.hibernate.Hibernate;
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
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("cant create user: "
                    + entity.getUsername());
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
            User user = session.get(User.class, id);
            Hibernate.initialize(user.getComments());
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Cant get user with id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String tableName = null;
        try (Session session = factory.openSession()) {
            Table tableAnnotation = Comment.class.getAnnotation(Table.class);
            tableName = tableAnnotation != null ? tableAnnotation.name()
                    : Comment.class.getSimpleName();
            return session.createNativeQuery("SELECT * FROM users", User.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all data from " + tableName);
        }
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
            throw new RuntimeException("Cant remove user: "
                    + entity.getUsername(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
