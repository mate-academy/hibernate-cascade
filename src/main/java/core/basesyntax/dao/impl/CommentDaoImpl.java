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
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();

            if (comment.getSmiles() != null) {
                comment.setSmiles(
                        comment.getSmiles().stream()
                                .map(smile -> {
                                    if (smile.getId() == null) {
                                        throw new RuntimeException(
                                                "Smile must exist in DB"
                                        );
                                    }
                                    return session.get(Smile.class, smile.getId());
                                })
                                .toList()
                );
            }

            session.persist(comment);
            transaction.commit();
            return comment;
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
            return session.createQuery("from Comment", Comment.class)
                    .getResultList();
        }
    }

    @Override
    public void remove(Comment comment) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.merge(comment));
            tx.commit();
        }
    }
}
