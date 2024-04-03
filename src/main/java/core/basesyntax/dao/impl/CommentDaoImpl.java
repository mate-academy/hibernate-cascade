package core.basesyntax.dao.impl;

import java.util.ArrayList;
import java.util.List;
import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't create comment");
            }
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Transaction transaction = null;
        Session session = factory.openSession();
        Comment comment = null;
        try {
            transaction = session.getTransaction();
            transaction.begin();
            comment = session.getReference(Comment.class, id);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't get comment by id");
            }
        } finally {
            session.close();
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        Transaction transaction = null;
        List<Comment> comments = new ArrayList<>();
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            comments = session.createQuery("from Smile", Comment.class).getResultList();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant get all comments");
        } finally {
            session.close();
        }
        return comments;
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove comment");
        } finally {
            session.close();
        }
    }
}
