package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends GenericDaoImpl<Comment> implements CommentDao {
    protected CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Comment.class, "SELECT c FROM Comment c");
    }
}
