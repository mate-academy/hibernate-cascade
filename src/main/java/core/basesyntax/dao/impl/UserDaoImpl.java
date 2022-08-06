package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Message;
import core.basesyntax.model.User;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create user: " + entity, e);
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        Session session = null;
        User user;
        try {
            session = factory.openSession();
            user = session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user with id: " + id, e);
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = null;
        List<User> users;
        try {
            session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);
            CriteriaQuery<User> all = criteriaQuery.select(userRoot);
            TypedQuery<User> allQuery = session.createQuery(all);
            users = allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users", e);
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        try {
            session = factory.openSession();
            session.delete(entity);
        }  catch (Exception e) {
            throw new RuntimeException("Can't remove user: " + entity, e);
        } finally {
            session.close();
        }
    }
}
