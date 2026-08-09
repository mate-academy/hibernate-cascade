package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
        if (entity == null) {
            throw new RuntimeException("User is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create new entity with a user in db: " + entity, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        if (id == null) {
            throw new RuntimeException("'id' is null");
        }
        User user;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Can't get entity of user from db by id = " + id);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> rootEntry = query.from(User.class);
            CriteriaQuery<User> all = query.select(rootEntry);
            users = session.createQuery(all).getResultList();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Can't get all entities from table 'users'");
        }
        return users;
    }

    @Override
    public void remove(User entity) {
        if (entity == null) {
            throw new RuntimeException("User is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove the user from db: " + entity, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
