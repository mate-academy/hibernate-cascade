package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
        if (entity == null) {
            throw new RuntimeException("Link to the comment is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create new entity with comment in db: " + entity, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        if (id == null) {
            throw new RuntimeException("This 'id' is null");
        }
        Comment comment;
        try (Session session = factory.openSession()) {
            comment = session.get(Comment.class, id);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Can't get entity of comment from db by id = " + id);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
            Root<Comment> rootEntry = query.from(Comment.class);
            CriteriaQuery<Comment> all = query.select(rootEntry);
            comments = session.createQuery(all).getResultList();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Can't get all entities from table 'comments'");
        }
        return comments;
    }

    @Override
    public void remove(Comment entity) {
        if (entity == null) {
            throw new RuntimeException("Link to the comment is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove the comment from db: " + entity, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
