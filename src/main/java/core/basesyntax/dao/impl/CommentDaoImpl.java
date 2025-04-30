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

            List<Smile> validatedSmiles = new ArrayList<>();

            if (entity.getSmiles() != null) {
                for (Smile smile : entity.getSmiles()) {
                    Smile existingSmile = session.get(Smile.class, smile.getId());

                    if (existingSmile != null) {
                        validatedSmiles.add(existingSmile);
                    } else {
                        throw new IllegalArgumentException("Smile " + smile.getId() + " not found");
                    }
                }
            }

            entity.setSmiles(validatedSmiles);
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create Comment: " + entity.toString(), e);
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
        Comment entity = null;

        try {
            session = factory.openSession();
            entity = (Comment) session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get Comment: " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> entityList;
        Session session = null;

        try {
            session = factory.openSession();
            entityList = session.createQuery("FROM Comment", Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cannot get Comments: " + e, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entityList;
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
            throw new RuntimeException("Cannot remove Comment: " + entity.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
