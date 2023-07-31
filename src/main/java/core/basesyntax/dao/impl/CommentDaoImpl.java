package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
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
            throw new RuntimeException("Can't add " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            Comment entity = session.get(Comment.class, id);
            return entity;
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get " + Comment.class + " by id " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        String query = "FROM Comment";
        try (Session session = factory.openSession()) {
            Query getAllQuery = session.createQuery(query, Comment.class);
            return getAllQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all comments.", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
