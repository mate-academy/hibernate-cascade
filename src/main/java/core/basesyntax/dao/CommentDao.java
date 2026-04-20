package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import core.basesyntax.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CommentDaoImpl implements CommentDao {

    @Override
    public Comment create(Comment entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Comment comment = session.get(Comment.class, id);
        session.close();
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Comment> list = session.createQuery("from Comment", Comment.class).getResultList();
        session.close();
        return list;
    }

    @Override
    public void remove(Comment entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.remove(entity);
        tx.commit();
        session.close();
    }
}

