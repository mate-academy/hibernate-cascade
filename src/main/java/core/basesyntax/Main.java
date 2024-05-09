package core.basesyntax;

import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Smile smileFirst = new Smile();
        smileFirst.setValue("by-by");
        Smile smileSecond = new Smile();
        smileSecond.setValue("fy-fy");

        Comment commentFirst = new Comment();
        commentFirst.setContent("tip top ty ru ru");
        commentFirst.setSmiles(List.of(smileFirst, smileSecond));

        User user = new User();
        user.setUsername("Bob");
        user.setComments(List.of(commentFirst));

        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);
        System.out.println(userDao.get(4L).toString());
    }
}
