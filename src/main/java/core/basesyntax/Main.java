package core.basesyntax;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
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

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        Smile smile1 = new Smile();
        smile1.setValue("Ylubka");

        Smile smile2 = new Smile();
        smile2.setValue("Pechal");

        smileDao.create(smile1);
        smileDao.create(smile2);

        Comment comment1 = new Comment();
        comment1.setContent("way");
        comment1.setSmiles(List.of(smileDao.get(1L), smileDao.get(2L)));

        Comment comment2 = new Comment();
        comment2.setContent("brr");
        comment2.setSmiles(List.of(smileDao.get(1L)));

        UserDao userDao = new UserDaoImpl(sessionFactory);
        User user1 = new User();
        user1.setUsername("Bob");
        user1.setComments(List.of(comment1, comment2));
        userDao.create(user1);

        User user2 = new User();
        user2.setUsername("Alice");
        user2.setComments(List.of(comment1));
        userDao.create(user2);
    }
}
