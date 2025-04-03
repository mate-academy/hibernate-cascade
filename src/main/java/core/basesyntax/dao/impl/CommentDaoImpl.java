package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.CommentDao;
import core.basesyntax.exception.DataProcessingException;
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
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert comment: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        Comment comment;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            comment = session.get(Comment.class, id);
            return comment;
        } catch (Exception e) {
            throw new DataProcessingException("Can't get comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            comments = session.createQuery("FROM Comment", Comment.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all comments", e);
        }
        return comments;
    }

    @Override
    public void remove(Comment entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't remove comment", e);
        } finally {
            session.close();
        }
    }
}
