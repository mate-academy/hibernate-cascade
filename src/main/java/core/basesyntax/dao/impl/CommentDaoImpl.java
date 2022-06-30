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
            return entity;
        } catch (RuntimeException cause) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create Comment: " + entity, cause);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        Session session = factory.openSession();
        return session.get(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        Session session = factory.openSession();
        String hql = "FROM Comment";
        Query<Comment> getAllCommentsQuery = session.createQuery(hql, Comment.class);
        return getAllCommentsQuery.getResultList();
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (RuntimeException cause) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
