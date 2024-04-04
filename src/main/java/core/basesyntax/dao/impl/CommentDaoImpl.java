package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    private Session session = null;
    private Transaction transaction = null;

    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error creating entity to the database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("can't get an Entity from the DB", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            comments = session.createQuery("FROM Comment", Comment.class).getResultList();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error retrieving all entities from the database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comments;
    }

    @Override
    public void remove(Comment entity) {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error removing entity to the database", e);
        } finally {
            session.close();
        }
    }
}
