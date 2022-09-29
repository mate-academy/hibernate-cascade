package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Comment comment = new Comment();
        comment.setContent("Hibernate cascades");
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        commentDao.create(comment);
        List<Comment> comments = commentDao.getAll();
        System.out.println(comments);
    }
}
