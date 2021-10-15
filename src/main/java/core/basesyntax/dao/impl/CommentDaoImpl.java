package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(comment + " wasn't added to DB!", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Comment with id: " + id + " wasn't found!", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            String hql = "FROM Comment ";
            Query query = session.createQuery(hql);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Comments weren't found!", e);
        }
    }

    @Override
    public void remove(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(comment + " wasn't deleted from DB!", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
