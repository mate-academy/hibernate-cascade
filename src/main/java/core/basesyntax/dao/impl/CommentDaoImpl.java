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
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = super.factory.openSession();
            transaction = currentSession.beginTransaction();
            currentSession.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create comment: " + entity,e);
        } finally {
            currentSession.close();
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session currentSession = super.factory.openSession();) {
            Comment comment = currentSession.get(Comment.class, id);
            return comment;
        } catch (Exception e) {
            throw new RuntimeException("Can't get by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session currentSession = super.factory.openSession();) {
            Query<Comment> commentsQuery =
                    currentSession.createQuery("from Comment", Comment.class);
            return commentsQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get comments", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = super.factory.openSession();
            transaction = currentSession.beginTransaction();
            currentSession.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete comment: " + entity,e);
        } finally {
            currentSession.close();
        }
    }
}
