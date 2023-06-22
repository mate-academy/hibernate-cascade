package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            }
            throw new RuntimeException("Was not able to create a new comment.", e);
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
            comment = session.get(Comment.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Was not able to get a comment by ID " 
                    + id + " from db.", e);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query<Comment> getAllQuery = 
                    session.createQuery("from Comment", Comment.class);
            comments = getAllQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Was not able to get all comments from db.", e);
        }
        return comments;
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
            throw new RuntimeException("Was not able to delete a comment by ID " 
                    + entity.getId() + " from db.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
