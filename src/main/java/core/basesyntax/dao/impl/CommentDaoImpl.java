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
    public Comment create(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("From Comment where id = :commentID");
            query.setParameter("commentID", entity.getId());
            if (((query).list().isEmpty())) {
                session.persist(entity);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("can`t add comment to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
            return entity;
        }
    }

    @Override
    public Comment get(Long id) {
        SessionFactory sessionFactory = factory.openSession().getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            return session.get(Comment.class, id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> getAllCommentsQuery = session.createQuery("from Comment", Comment.class);
            return getAllCommentsQuery.getResultList();
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t remove comment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
