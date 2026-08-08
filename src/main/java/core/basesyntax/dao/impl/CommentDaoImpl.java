package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        if (entity == null) {
            throw new RuntimeException("Comment can't be null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            List<Smile> managed = new ArrayList<>();
            if (entity.getSmiles() == null) {
                entity.setSmiles(new ArrayList<>());
            } else {
                for (Smile smile : entity.getSmiles()) {
                    if (smile.getId() == null) {
                        throw new RuntimeException("Smile doesn't exist");
                    }
                    Smile smilesFromDb = session.get(Smile.class, smile.getId());
                    if (smilesFromDb == null) {
                        throw new RuntimeException("Smile doesn't exist");
                    }
                    managed.add(smilesFromDb);
                }
            }
            entity.setSmiles(managed);
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create comment " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        if (id == null) {
            throw new RuntimeException("Id can't be null");
        }
        Comment commentFromDb;
        try (Session session = factory.openSession()) {
            commentFromDb = session.get(Comment.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Can't find Comment by id " + id, e);
        }
        return commentFromDb;
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Comment", Comment.class).list();
        } catch (HibernateException e) {
            throw new RuntimeException("Can't get comments from DB", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        if (entity == null) {
            throw new RuntimeException("Comment can't be null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Comment commentFromDb = session.get(Comment.class, entity.getId());
            if (commentFromDb == null) {
                throw new RuntimeException("Comment with id " + entity.getId() + " is null");
            }
            if (commentFromDb.getSmiles() != null) {
                commentFromDb.getSmiles().clear();
            }
            session.remove(commentFromDb);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove Comment " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
