package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
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
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            List<Smile> existingSmiles = new ArrayList<>();

            if (entity.getSmiles() != null) {
                for (Smile smile : entity.getSmiles()) {
                    if (smile.getId() == null) {
                        throw new RuntimeException("Smile must exist in DB");
                    }
                    Smile smileFromDb = session.get(Smile.class, smile.getId());
                    if (smileFromDb == null) {
                        throw new RuntimeException("Smile with id "
                                + smile.getId() + " not found in DB");
                    }
                    existingSmiles.add(smileFromDb);
                }
            }

            entity.setSmiles(existingSmiles);
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Session session = null;
        Comment comment = null;
        try {
            session = factory.openSession();
            comment = session.get(Comment.class, id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        Session session = null;
        List<Comment> comments = new ArrayList<>();

        try {
            session = factory.openSession();
            comments = session.createQuery("from Comment", Comment.class).list();
        } finally {
            if (session != null) {
                session.close();
            }
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
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
