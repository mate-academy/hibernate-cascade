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
        } catch (RuntimeException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction was rollback, "
                    + exception);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        Comment comment;
        try (Session session = factory.openSession()) {
            comment = session.get(Comment.class, id);
        } catch (RuntimeException exception) {
            throw new RuntimeException("There is no way to get Comment by id: "
                    + id + ", ", exception);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments;
        try (Session session = factory.openSession()) {
            comments = session.createQuery("FROM Comment", Comment.class).getResultList();
        } catch (RuntimeException exception) {
            throw new RuntimeException("There is no way to get all comments, ",
                    exception);
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
        } catch (RuntimeException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction was rollback, ",
                    exception);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
    }
}
