package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Smile smile = new Smile();
        smile.setValue("smile");
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        smileDao.create(smile);

        Comment comment = new Comment();
        comment.setContent("comment");
        comment.setSmiles(List.of(smile));

        Comment comment2 = new Comment();
        comment2.setContent("comment2");
        comment2.setSmiles(List.of(smile));

        User user = new User();
        user.setUsername("User");
        user.setComments(List.of(comment));

        User user2 = new User();
        user2.setUsername("User2");
        user2.setComments(List.of(comment2));

        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);
        userDao.create(user2);

        System.out.println(userDao.getAll());

        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        commentDao.remove(comment2);
        System.out.println(userDao.getAll());
        userDao.remove(user2);

        System.out.println(userDao.getAll());
    }
}
