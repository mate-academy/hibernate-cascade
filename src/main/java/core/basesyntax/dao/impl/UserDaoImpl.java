package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDaoImpl extends AbstractDao implements UserDao {
    private Session session = null;
    private Transaction transaction = null;

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error creating entity to the database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            Query<User> getEntityQuery = session.createQuery("from User u "
                    + "left join fetch u.comments " + "where u.id = :id");
            getEntityQuery.setParameter("id", id);
            return getEntityQuery.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("can't get an Entity from the DB", e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User u "
                    + "left join fetch u.comments ",
                    User.class).getResultList();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error retrieving all entities from the database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public void remove(User entity) {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error removing entity to the database", e);
        } finally {
            session.close();
        }
    }
}
