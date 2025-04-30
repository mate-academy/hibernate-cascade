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
    public Comment create(Comment comment) {
        Transaction tx = null;

        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.persist(comment);
            tx.commit();
            return comment;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory
                .openSession()) {
            return session.get(Comment.class, id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory
                .openSession()) {
            return session.createQuery("FROM Comment ORDER BY id", Comment.class)
                    .list();
        }
    }

    @Override
    public void remove(Comment comment) {
        Transaction tx = null;

        try (Session session = factory
                .openSession()) {
            tx = session.beginTransaction();
            session.remove(comment);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
