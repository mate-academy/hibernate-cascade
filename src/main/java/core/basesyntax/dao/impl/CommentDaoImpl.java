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
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save comment in DB", e);
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        Comment comment;
        try (Session session = factory.openSession()) {
            comment = session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment with id=" + id + " from DB", e);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> getAllCommentsQuery = session.createQuery(
                    "from Comment", Comment.class);
            return getAllCommentsQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comments from DB", e);
        }
    }

    @Override
    public void remove(Comment comment) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete comment from DB", e);
        }
    }
}
