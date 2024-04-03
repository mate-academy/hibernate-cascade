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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while creating entity");
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            try {
                return session.get(User.class, id);
            } catch (Exception e) {
                throw new RuntimeException("cannot getting User by id " + id, e);
            }
        } catch (Exception e) {
            throw new RuntimeException("cannot closing session", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            String hql = "FROM User";
            Query<User> query = session.createQuery(hql, User.class);
            List<User> resultList = query.getResultList();
            return new ArrayList<>(resultList);
        } catch (Exception e) {
            throw new RuntimeException("Error getting all Users", e);
        }
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, entity.getId());
            if (user != null) {
                session.delete(user);
            } else {
                throw new RuntimeException("Entity not found with id: " + entity.getId());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete entity " + entity, e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
