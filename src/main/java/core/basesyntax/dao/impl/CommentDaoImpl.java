package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
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
        return persistEntity(entity);
    }

    @Override
    public Comment get(Long id) {
        return findById(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        return getEntities(Comment.class);
    }

    @Override
    public void remove(Comment entity) {

        removeEntity(entity);
    }
}
