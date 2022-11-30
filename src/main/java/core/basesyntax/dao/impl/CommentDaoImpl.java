package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create entity",e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        Comment comment = null;
        try (Session session = factory.openSession()) {
            comment = session.get(Comment.class, id);
            return comment;
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get comment by id", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            TypedQuery<Comment> getAllList = session.createQuery("from Comment", Comment.class);
            return getAllList.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get list", e);
        }
    }

    @Override
    public void remove(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
