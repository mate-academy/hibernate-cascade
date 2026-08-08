package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import java.util.ArrayList;
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
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            if (entity.getComments() != null && !entity.getComments().isEmpty()) {
                for (Comment comment : entity.getComments()) {
                    if (comment.getSmiles() != null) {
                        List<Smile> managedSmiles = new ArrayList<>();
                        for (Smile smile : comment.getSmiles()) {
                            Smile managedSmile = session.createQuery(
                                            "FROM Smile s WHERE s.value = :value", Smile.class)
                                    .setParameter("value", smile.getValue())
                                    .uniqueResult();
                            if (managedSmile != null) {
                                managedSmiles.add(managedSmile);
                            } else {
                                throw new RuntimeException("Smile with value "
                                        + smile.getValue() + " doesn't exist in DB");
                            }
                        }
                        comment.setSmiles(managedSmiles);
                    }
                    session.persist(comment);
                }
            }

            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create user: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users", e);
        }
    }

    @Override
    public void remove(User entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove user: " + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
