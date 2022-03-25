package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
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
            session.save(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't create the comment in DB. Comment: " + entity, e);
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
            throw new RuntimeException("Couldn't get the comment from DB by id: " + id, e);
        }
        if (comment == null) {
            throw new RuntimeException("There is no comment in DB by such id: " + id);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> getAllCommentsQuery =
                    session.createQuery("from Comment", Comment.class);
            return getAllCommentsQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Couldn't get all comments from DB");
        }
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't delete the comment from DB."
                    + " Comment: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
