package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment get(Long id) {
        Session session = super.factory.openSession();
        Comment comment = session.get(Comment.class, id);
        session.close();
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = super.factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).getResultList();
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't remove comment: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
