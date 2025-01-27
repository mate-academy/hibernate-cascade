package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.SessionFactory;


public class CommentDaoImpl extends AbstractDao
        implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        return executeInsideTransaction(session -> {
            session.persist(entity);
            return entity;
        });
    }

    @Override
    public Comment get(Long id) {
        return executeInsideTransaction(session -> session.createQuery(
                        "SELECT c FROM Comment c LEFT"
                                + " JOIN FETCH"
                                + " c.smiles WHERE c.id = :id", Comment.class)
                .setParameter("id", id)
                .uniqueResult());
    }

    @Override
    public List<Comment> getAll() {
        return executeInsideTransaction(session ->
                session.createQuery("FROM Comment", Comment.class).list());
    }

    @Override
    public void remove(Comment entity) {
        executeInsideTransaction(session -> {
            Comment comment = session.merge(entity);
            session.remove(comment);
            return null;
        });
    }
}
