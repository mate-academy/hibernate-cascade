package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
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

    public CommentDaoImpl() {
        super(HibernateUtil.getSessionFactory());
    }

    @Override
    public Comment create(Comment entity) {
        try (Session session = factory.openSession()) {
            List<Smile> existingSmiles = new ArrayList<>();
            for (Smile smile : entity.getSmiles()) {
                Smile attachedSmile = session.get(Smile.class, smile.getId());
                if (attachedSmile == null) {
                    throw new RuntimeException("Smile with id "
                            + smile.getId() + " does not exist");
                }
                existingSmiles.add(attachedSmile);
            }
            entity.setSmiles(existingSmiles);
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        }
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
            Transaction transaction = session.beginTransaction();
            List<Comment> comments = session.createQuery("from Comment", Comment.class).list();
            transaction.commit();
            return comments;
        }
    }

    @Override
    public void remove(Comment entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Comment comment = session.get(Comment.class, entity.getId());
            if (comment != null) {
                session.delete(comment);
            }
            transaction.commit();
        }
    }
}
