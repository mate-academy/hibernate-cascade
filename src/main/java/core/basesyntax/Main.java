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
import core.basesyntax.util.HibernateUtil;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Smile smile = new Smile();
        smile.setValue("FirstSmile");

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        smileDao.create(smile);
        //System.out.println(smileDao.get(smile.getId()));

        System.out.println(smileDao.getAll());

        Comment comment = new Comment();
        comment.setContent("Comment with smiles of User");
        comment.setSmiles(List.of(smile));
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        commentDao.create(comment);

        User user = new User();
        user.setUsername("Bob");
        user.setComments(List.of(comment));
        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);

    }
}
