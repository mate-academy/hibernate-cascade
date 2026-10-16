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
    public Comment create(Comment com) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(com);
            transaction.commit();
            return com;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Can't create new comment " + com, e);
        } finally {
            session.close();
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find comment with id " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        String sql = "SELECT * FROM comment";
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(sql, Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comments ", e);
        }
    }

    @Override
    public void remove(Comment com) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(com);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Can't remove comment " + com, e);
        } finally {
            session.close();
        }
    }
}
