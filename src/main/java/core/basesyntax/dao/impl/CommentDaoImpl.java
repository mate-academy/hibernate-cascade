package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;

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
        } catch (PersistenceException persistenceException) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't save comment: "
                    + entity, persistenceException);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Comment comment;
        try (Session session = factory.openSession()) {
            comment = session.get(Comment.class, id);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> allComments;
        try (Session session = factory.openSession()) {
            Query<Comment> allCommentsQuery = session
                    .createQuery("from Comment", Comment.class);
            allComments = allCommentsQuery.getResultList();
        } catch (PersistenceException persistenceException) {
            throw new RuntimeException("Couldn't get all comments from DB",
                    persistenceException);
        }

        return allComments;
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
        } catch (PersistenceException persistenceException) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't remove comment: "
                    + entity, persistenceException);
        }  finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
