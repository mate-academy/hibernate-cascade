package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment comment) {
        save(comment);
        return comment;
    }

    @Override
    public Comment get(Long id) {
        return findById(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Comment", Comment.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all comments", e);
        }
    }

    @Override
    public void remove(Comment comment) {
        delete(comment);
    }
}
