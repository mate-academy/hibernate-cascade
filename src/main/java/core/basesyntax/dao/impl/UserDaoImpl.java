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
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            prepareComments(session, entity.getComments());
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create user: " + entity, e);
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
            return session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users", e);
        }
    }

    @Override
    public void remove(User entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, entity.getId());
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove user: " + entity, e);
        }
    }

    private void prepareComments(Session session, List<Comment> comments) {
        if (comments == null) {
            return;
        }
        for (Comment comment : comments) {
            comment.setSmiles(getManagedSmiles(session, comment.getSmiles()));
        }
    }

    private List<Smile> getManagedSmiles(Session session, List<Smile> smiles) {
        List<Smile> managedSmiles = new ArrayList<>();
        if (smiles == null) {
            return managedSmiles;
        }
        for (Smile smile : smiles) {
            if (smile.getId() == null) {
                throw new RuntimeException("Smile must already exist in DB");
            }
            Smile managedSmile = session.get(Smile.class, smile.getId());
            if (managedSmile == null) {
                throw new RuntimeException("Smile with id " + smile.getId() + " doesn't exist");
            }
            managedSmiles.add(managedSmile);
        }
        return managedSmiles;
    }
}
