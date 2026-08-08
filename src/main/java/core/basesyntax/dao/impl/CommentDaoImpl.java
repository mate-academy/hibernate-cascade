package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(String.format(
                    "An error occurred while trying to add a comment %s to the database",
                    comment), ex);
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(Comment.class, id))
                    .orElseThrow(() -> new RuntimeException(String.format(
                    "An error occurred while trying to retrieve a comment "
                            + "with ID %d from the database", id)));
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM Comment a", Comment.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while trying to retrieve all comments "
                    + "from the database", ex);
        }
    }

    @Override
    public void remove(Comment comment) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(String.format("An error occurred while trying to remove "
                    + "a comment %s from the database", comment), ex);
        }
    }
}
