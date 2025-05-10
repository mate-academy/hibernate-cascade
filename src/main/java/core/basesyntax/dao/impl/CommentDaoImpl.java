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

    private Session getSession() {
        return factory.openSession();
    }

    @Override
    public Comment create(Comment entity) {
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to create Message", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Comment get(Long id) {
        Transaction transaction = null;
        Comment comment;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            comment = session.get(Comment.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to find Comment by ID", e);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        Session session = getSession();
        try {
            return session.createQuery("FROM Comment", Comment.class).list();
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(Comment comment) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove Comment", e);
        }
    }
}
