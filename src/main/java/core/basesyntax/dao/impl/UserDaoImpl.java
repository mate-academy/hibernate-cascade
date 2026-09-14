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
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                return entity;
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't create User", e);
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from User e left join fetch e.comments where e.id = :id",
                    User.class).setParameter("id", id).uniqueResult();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get User by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from User e left join fetch e.comments order by e.id",
                    User.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all User entities", e);
        }
    }

    @Override
    public void remove(User entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User managed = session.get(User.class, entity.getId());
                if (managed != null) {
                    session.remove(managed);
                }
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't remove User", e);
        }
    }
}
