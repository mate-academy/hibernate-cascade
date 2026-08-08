package core.basesyntax.dao.impl;

import core.basesyntax.DataProcessingException;
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
        if (comment == null) {
            throw new RuntimeException("unacceptable data");
        }

        if (comment.getId() != null && get(comment.getId()) != null) {
            throw new RuntimeException(comment + "already existed in database");
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("adding " + comment + " into database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        Session session = null;
        Comment comment = null;
        try {
            session = factory.openSession();
            comment = session.get(Comment.class, id);
        } catch (Exception e) {

            throw new DataProcessingException("getting comment with "
                    + id + " from database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).getResultList();
        }
    }

    @Override
    public void remove(Comment comment) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(get(comment.getId()));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("removing comment: "
                    + comment + " from database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
