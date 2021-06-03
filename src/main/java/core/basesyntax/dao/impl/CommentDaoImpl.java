package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.ArrayList;
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
    public Comment create(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(comment);
            transaction.commit();
            return comment;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't create a comment: " + comment, e);
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
            throw new RuntimeException("Couldn't get comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("FROM Comment", Comment.class);
            comments.addAll(query.getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get all comments", e);
        }
        return comments;
    }

    @Override
    public void remove(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't remove a comment: " + comment, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
