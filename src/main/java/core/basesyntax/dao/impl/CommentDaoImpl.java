package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            throw new RuntimeException("Can not save in DB ", e);
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
            Comment comment = session.get(Comment.class, id);
            Hibernate.initialize(comment.getSmiles());
            return comment;

        } catch (Exception e) {
            throw new RuntimeException("Can not find an object with ID " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            String hql = "FROM Comment";
            Query<Comment> query = session.createQuery(hql, Comment.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can not get All object ", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }
    }
}
