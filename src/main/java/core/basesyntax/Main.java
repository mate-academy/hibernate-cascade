package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoImpl(HibernateUtil.getSessionFactory());
        CommentDao commentDao = new CommentDaoImpl(HibernateUtil.getSessionFactory());
        Comment comment = new Comment();
        comment.setContent("sfsfds");
        Comment comment1 = new Comment();
        comment1.setContent("dfsdfs");

        User user = new User();
        user.setUsername("Sasha");
        user.setComments(List.of(comment, comment1));
        System.out.println(userDao.create(user));
    }
}
