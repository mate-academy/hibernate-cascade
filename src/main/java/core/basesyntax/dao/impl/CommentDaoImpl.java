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
    public Comment create(Comment comment) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();;
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();
            return comment;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Unable to add " + comment + "to DB", e);
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
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve Comment from DB, id=" + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> commentQuery = session.createQuery("from comments", Comment.class);
            return commentQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve all Comments from DB", e);
        }
    }

    @Override
    public void remove(Comment comment) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();;
            transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Unable to remove " + comment + " from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
