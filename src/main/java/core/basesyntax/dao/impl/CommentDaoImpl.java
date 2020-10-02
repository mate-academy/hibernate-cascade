package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Comment create(Comment comment) {
        return super.create(comment);
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.load(Comment.class, id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> getAllMoviesQuery = session.createQuery("from Comment", Comment.class);
            return getAllMoviesQuery.getResultList();
        }
    }

    public void remove(Comment comment) {
        super.remove(comment);
    }
}
