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
    public Comment create(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t create comment:" + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Comment.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can`t get comment by id:" + id, e);
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public List<Comment> getAll() {
        String query = "SELECT c FROM Comment c";
        Session session = null;
        try {
            session = factory.openSession();
            Query<Comment> comments = session.createQuery(query, Comment.class);
            return comments.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can`t get all comments from db");
        } finally {
            assert session != null;
            session.close();
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t remove comment:" + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
