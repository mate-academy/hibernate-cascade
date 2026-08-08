package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
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
            session.persist(entity);
            session.flush();
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant create user: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.find(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cant get user with id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            jakarta.persistence.criteria.CriteriaBuilder cb = session.getCriteriaBuilder();
            jakarta.persistence.criteria.CriteriaQuery<User> cr = cb.createQuery(User.class);
            jakarta.persistence.criteria.Root<User> root = cr.from(User.class);
            cr.select(root);

            return session.createQuery(cr).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all uesrs", e);
        }
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction transaction = null;
        Long id = entity.getId();
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            session.remove(user);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("error removing user with id: " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
