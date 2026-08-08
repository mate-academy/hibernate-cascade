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
    public Comment create(Comment entity) {
        isEntityNull(entity);
        areSmilesPersisted(entity);
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
            throw new RuntimeException("Can't save comment to DB; comment: "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    private void isEntityNull(Comment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("You can't save null comment.");
        }
    }

    private void areSmilesPersisted(Comment entity) {
        List<Smile> smiles = entity.getSmiles();
        if (smiles != null) {
            for (Smile smile : smiles) {
                if (smile != null && smile.getId() == null) {
                    throw new IllegalArgumentException("Can't save comment to DB if the smile "
                            + "is not stored in DB. " + entity);
                }
            }
        }
    }

    private void isCommentNull(Comment entity) {
        if (entity == null) {
            throw new RuntimeException("Comment is null: " + entity);
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment by id: "
            + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment", Comment.class)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("Can't get list of all comments from DB",
                    e);
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
            throw new RuntimeException("Can't remove comment from DB: "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
