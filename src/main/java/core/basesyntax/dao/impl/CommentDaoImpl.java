package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Comment to DB: " + comment);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        Comment dbComment = null;
        try (Session session = factory.openSession()) {
            dbComment = session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Comment by id: " + id);
        }
        return dbComment;
    }

    @Override
    public List<Comment> getAll() {
        Session session = null;
        List<Comment> comments = null;
        try {
            session = factory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Comment> criteria = builder.createQuery(Comment.class);
            criteria.from(Comment.class);
            comments = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all Comments from DB. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
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
            throw new RuntimeException("Cant delete Comment from DB: " + comment, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
