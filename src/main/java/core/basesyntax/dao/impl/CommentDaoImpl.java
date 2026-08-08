package core.basesyntax.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;

public class CommentDaoImpl implements CommentDao {
    private final SessionFactory sessionFactory;

    public CommentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CommentDaoImpl() {
        this(HibernateUtil.getSessionFactory());
    }

    @Override
    public Comment create(Comment entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't create comment", e);
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Comment.class, id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Comment", Comment.class).getResultList();
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't remove comment", e);
        }
    }
}