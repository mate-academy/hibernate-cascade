package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        return executeInsideTransaction(session -> {
            session.save(entity);
            return entity;
        });
    }

    @Override
    public Comment get(Long id) {
        return executeInsideTransaction(session -> session.get(Comment.class, id));
    }

    @Override
    public List<Comment> getAll() {
        return executeInsideTransaction(session -> session.createQuery("FROM Comment", Comment.class).list());
    }

    @Override
    public void remove(Comment entity) {
        executeInsideTransaction(session -> {
            Comment comment = session.merge(entity); // Слияние сессии
            if (comment.getSmiles() != null && !comment.getSmiles().isEmpty()) {
                comment.getSmiles().clear(); // Удаляем связи с существующими смайлами
            }
            session.remove(comment); // Удаляем сам комментарий
            return null;
        });
    }
}