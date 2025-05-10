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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Something is wrong with writing the COMMENT: "
                    + System.lineSeparator()
                    + entity
                    + System.lineSeparator()
                    + " row to database. Closing connection and rolling back the transaction.", e);
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
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Something is wrong with getting data from DB for COMMENT."
                    + "Maybe such ID as: " + id + " is absent in corresponding DB table", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("select a from comments a", Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Something is wrong with getting ALL the data from DB for COMMENT."
                            + "Maybe corresponding table is corrupted", e);
        }
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Something is wrong with removing the COMMENT: "
                    + System.lineSeparator()
                    + entity
                    + System.lineSeparator()
                    + " row from database. Closing connection and rolling back the transaction.",
                    e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
