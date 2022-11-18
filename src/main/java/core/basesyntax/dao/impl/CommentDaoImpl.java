package core.basesyntax.dao.impl;

import java.util.List;
import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
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
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save comment to DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try(Session session = factory.openSession()){
            return session.get(Comment.class,id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try(Session session = factory.openSession()){
            String query = "from Comment";
            Query<Comment> getAllCommentsQuery = session.createQuery(query, Comment.class);
            return getAllCommentsQuery.getResultList();
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save remove comment from DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
