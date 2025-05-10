package core.basesyntax;

import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.model.Comment;

public class Main {
    public static void main(String[] args) {
        CommentDaoImpl commentDao = new CommentDaoImpl(HibernateUtil.getSessionFactory());
        Comment comment = new Comment();
        comment.setContent("Hello comment");
        commentDao.create(comment);
    }
}
