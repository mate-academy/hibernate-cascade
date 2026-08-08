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

public class Main {
    public static void main(String[] args) {
        SmileDao smileDao = new SmileDaoImpl(HibernateUtil.getSessionFactory());

        Smile smile = new Smile();
        smile.setValue(":)");
        smileDao.create(smile);

        CommentDao commentDao = new CommentDaoImpl(HibernateUtil.getSessionFactory());
        Comment comment = new Comment();
        comment.setContent("Hi, my name is Igor");
        comment.setSmiles(smileDao.getAll());
        commentDao.create(comment);

        UserDao userDao = new UserDaoImpl(HibernateUtil.getSessionFactory());
        User igor = new User();
        igor.setComments(commentDao.getAll());
        userDao.create(igor);
        System.out.println(userDao.get(1L));
    }
}
