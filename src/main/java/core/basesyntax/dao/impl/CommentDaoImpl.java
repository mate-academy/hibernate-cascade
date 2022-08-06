package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create comment: " + entity, e);
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Session session = null;
        Comment comment;
        try {
            session = factory.openSession();
            comment = session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment with id: " + id, e);
        } finally {
            session.close();
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        Session session = null;
        List<Comment> comments;
        try {
            session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
            Root<Comment> commentRoot = criteriaQuery.from(Comment.class);
            CriteriaQuery<Comment> all = criteriaQuery.select(commentRoot);
            TypedQuery<Comment> allQuery = session.createQuery(all);
            comments = allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comments", e);
        } finally {
            session.close();
        }
        return comments;
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
            throw new RuntimeException("Can't get all comments", e);
        } finally {
            session.close();
        }
    }
}
