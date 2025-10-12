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
    public Comment create(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't create comment " + comment + " ,transaction rollback.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {

        try (Session session = factory.openSession()) {
            Comment comment = session.find(Comment.class, id);
            if (comment != null) {
                return comment;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    "Can't get comment with id " + id + " from database", e);
        }
        return null;
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            List<Comment> commentsFromDb = session.createQuery("FROM Comment ", Comment.class).list();
            if (commentsFromDb != null) {
                return commentsFromDb;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    "Can't get list of comments from database", e);
        }
        return null;
    }

    @Override
    public void remove(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(comment);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                "Can't remove comment " + comment + " ,transaction rollback.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
