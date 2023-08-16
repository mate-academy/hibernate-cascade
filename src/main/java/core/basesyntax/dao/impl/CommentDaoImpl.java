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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Can't add comment "
                    + comment.getContent() + " to DB");
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
            return session.get(Comment.class, id);
        } catch (Throwable e) {
            throw new RuntimeException("Can't get comment by provided id " + id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> getAllFromCommentQuery
                    = session.createQuery("FROM Comment", Comment.class);
            return getAllFromCommentQuery.getResultList();
        } catch (Throwable e) {
            throw new RuntimeException("Can't get all comments");
        }
    }

    @Override
    public void remove(Comment comment) {
        Transaction transaction;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Throwable e) {
            throw new RuntimeException("Can't remove comment "
                    + comment.getContent());
        }
    }
}
