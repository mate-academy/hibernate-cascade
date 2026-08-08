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
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert comment entity", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Comment get(Long id) {
        Session session = factory.openSession();
        try {
            Comment comment = session.get(Comment.class, id);
            return comment;
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment by id" + id, e);
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
            Comment comment = session.get(Comment.class, entity.getId());
            if (comment != null) {
                session.remove(comment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete comment entity", e);
        } finally {
            session.close();
        }
    }
}
