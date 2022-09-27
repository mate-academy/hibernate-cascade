package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.SessionFactory;

public class Runner {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Comment comment = new Comment();
        comment.setContent("This is so funny!");
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);

        List<Comment> all = commentDao.getAll();
        System.out.println(all);
    }
}
