package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not persist comment: " + entity, e);
        } finally {
            session.close();
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            Comment comment = session.get(Comment.class, id);
            return comment;
        } catch (Exception e) {
            throw new RuntimeException("Could not find comment with id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            List<Comment> commentsList
                    = session.createQuery("From Comment", Comment.class).getResultList();
            return commentsList;
        } catch (RuntimeException e) {
            throw new RuntimeException("Could not return comments list", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not remove comment: " + entity, e);
        } finally {
            session.close();
        }
    }
}
