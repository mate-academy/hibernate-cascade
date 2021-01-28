package core.basesyntax.dao.impl;

import core.basesyntax.model.Comment;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
