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
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Exception was occurred during create the Comment: "
                    + entity.getContent(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception was occurred during getting Comment by id:"
                    + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        String hql = "FROM Comment";
        try (Session session = factory.openSession()) {
            Query<Comment> getAllCommentsQuery = session.createQuery(hql, Comment.class);
            return getAllCommentsQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception was occurred during getting all Comments", e);
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Exception was occurred during removing the Comment: "
                    + entity.getContent(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
