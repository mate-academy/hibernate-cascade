package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            return entity;
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create Comment " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get Comment with id " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        String query = "FROM Comment";
        try (Session session = factory.openSession()) {
            Query<Comment> getAllCommentsQuery = session.createQuery(query, Comment.class);
            return getAllCommentsQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all Comments", e);
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove Comment " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
