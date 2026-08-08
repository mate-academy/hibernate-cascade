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
            throw new RuntimeException("Can't add comment to database.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Session session = factory.openSession();
        try {
            Comment comment = session.get(Comment.class, id);
            return comment;
        } catch (Exception e) {
            throw new RuntimeException("Can not get movie", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Comment> getAll() {
        Session session = factory.openSession();
        try {
            return session.createQuery("FROM Comment", Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get list of comments", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(session.get(Comment.class, entity.getId()));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete comment", e);
        } finally {
            session.close();
        }
    }
}
