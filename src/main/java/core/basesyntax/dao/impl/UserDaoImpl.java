package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;

        try {
            entityManager = factory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't save user to DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return entity;
    }

    @Override
    public User get(Long id) {
        User user;

        EntityManager entityManager = null;

        try {
            entityManager = factory.createEntityManager();
            user = entityManager.find(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve user from DB with id: " + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        EntityManager entityManager = null;
        List<User> list = new ArrayList<>();

        try {
            entityManager = factory.createEntityManager();
            String hql = "FROM User";
            Query query = entityManager.createQuery(hql);
            list.addAll((List<User>) query.getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Could not get all users from DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return list;
    }

    @Override
    public void remove(User entity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;

        try {
            entityManager = factory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't remove user from DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
