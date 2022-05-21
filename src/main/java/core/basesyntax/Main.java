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
        Smile oneSmile = new Smile();
        oneSmile.setValue("one");
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        smileDao.create(oneSmile);

        User bob = new User();
        bob.setUsername("Bob");

        Comment firstBobComment = new Comment();
        firstBobComment.setContent("first comment bob");
        firstBobComment.setSmiles(List.of(oneSmile));
        bob.setComments(List.of(firstBobComment));

        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(bob);
    }
}
