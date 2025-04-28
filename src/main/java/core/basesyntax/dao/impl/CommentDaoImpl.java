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
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t save entity:" + entity,e);
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
            Comment result = session.get(Comment.class, id);
            if (result != null) {
                return result;
            }
            throw new RuntimeException("Cannot find comment by id " + id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            List<Comment> comments = session.createQuery("from Comment").list();
            return comments;
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete comment " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
