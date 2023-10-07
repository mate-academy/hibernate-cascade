package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import jakarta.persistence.Query;
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
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add user - " + entity
                    + " to DB", e);
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
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user with id - "
                    + id
                    + " from DB", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            String hql = "from User";
            Query getAllUsers = session.createQuery(hql, User.class);
            return getAllUsers.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users from DB", e);
        }
    }

    @Override
    public void remove(User entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove user - " + entity
                    + " from DB", e);
        }
    }
}


/*
package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.User;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
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
            throw new RuntimeException("Can't add an user - " + entity
                    + " to DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.getReference(User.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can't get user with id - "
                    + id
                    + " from DB", e);
        }
    }

    @Override
    public List<User> getAll() {
        return null;
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
            throw new RuntimeException("Can't remove user - " + entity
                    + " from DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}

 */
