package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        final CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        final SmileDao smileDao = new SmileDaoImpl(sessionFactory);

        Smile smile1 = new Smile(":)");
        Smile smile2 = new Smile("*_*");
        smileDao.create(smile1);
        smileDao.create(smile2);

        Comment comment = new Comment();
        comment.setContent("Hello");
        comment.setSmiles(List.of(smile1, smile2));

        commentDao.create(comment);

        List<Comment> comments = commentDao.getAll();
    }
}
