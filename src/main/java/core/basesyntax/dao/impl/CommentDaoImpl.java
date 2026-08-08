package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
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
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            if (comment.getSmiles() != null) {
                List<Smile> managedSmiles = new java.util.ArrayList<>();
                for (Smile smile : comment.getSmiles()) {
                    if (smile.getId() == null) {
                        throw new RuntimeException(
                                "Smile must exist in DB before adding to comment");
                    }
                    Smile managed = session.get(Smile.class, smile.getId());
                    if (managed == null) {
                        throw new RuntimeException(
                                "Smile not found in DB: id=" + smile.getId());
                    }
                    managedSmiles.add(managed);
                }
                comment.setSmiles(managedSmiles);
            }

            session.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Comment", Comment.class)
                    .getResultList();
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            Comment comment = session.get(Comment.class, entity.getId());
            if (comment != null) {
                session.remove(comment);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
