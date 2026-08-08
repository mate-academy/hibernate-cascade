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
    public Comment create(Comment entity) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.persist(entity);

        tx.commit();
        session.close();
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Session session = factory.openSession();
        Comment comment = session.get(Comment.class, id);
        session.close();
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        Session session = factory.openSession();
        List<Comment> comments = session
                .createQuery("FROM Comment", Comment.class)
                .getResultList();
        session.close();
        return comments;
    }

    @Override
    public void remove(Comment comment) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Comment managed = session.get(Comment.class, comment.getId());
        session.remove(managed);

        tx.commit();
        session.close();
    }
}
