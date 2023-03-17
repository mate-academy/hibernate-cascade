package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
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
    public Comment create(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't add a comment to DB " + entity, e);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Comment comment = null;
        try (Session session = factory.openSession()) {
            comment = session.find(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find comment by id " + id, e);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        List commentList = null;
        try (Session session = factory.openSession()) {
            Query getAllCommentsQuery = session.createQuery("from Comment", Comment.class);
            commentList = getAllCommentsQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comments from DB", e);
        }
        return commentList;
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove comments from DB " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
