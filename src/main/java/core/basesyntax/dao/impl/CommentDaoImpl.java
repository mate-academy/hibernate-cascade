package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Transaction tx = null;
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            session.save(entity);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Error while creating comment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Transaction tx = null;
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            Comment comment = session.get(Comment.class, id);

            tx.commit();
            return comment;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Error while getting comment by id " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Comment> getAll() {
        Transaction tx = null;
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            List<Comment> comments = session.createQuery("from Comment", Comment.class).getResultList();

            tx.commit();
            return comments;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Error while getting all comments", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction tx = null;
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            session.remove(entity);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Error while removing comment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
