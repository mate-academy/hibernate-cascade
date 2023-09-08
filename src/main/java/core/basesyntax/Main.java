package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.model.Comment;

public class Main {
    public static void main(String[] args) {
        CommentDao commentDao = new CommentDaoImpl(HibernateUtil.getSessionFactory());
        Comment comment = new Comment();
        comment.setContent("test okkkk");
//        commentDao.create(comment);
        System.out.println(commentDao.getAll());

    }
}
