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
    public Comment create(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add comment: " + comment, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            Query<Comment> getCommentQuery = session.createQuery("from Comment c left join fetch "
                    + "c.smiles where c.id = :id", Comment.class);
            getCommentQuery.setParameter("id", id);
            return getCommentQuery.getSingleResult();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get from DB comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> allCommentsList = session.createQuery("from Comment", Comment.class);
            return allCommentsList.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get from DB list of all comments", e);
        }
    }

    @Override
    public void remove(Comment comment) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't delete comment from DB. Comment: " + comment, e);
        }

    }
}
