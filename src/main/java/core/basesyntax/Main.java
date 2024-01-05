package core.basesyntax;

import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        UserDao userDao = new UserDaoImpl(sessionFactory);

        //      User user = new User();
        //
        //      Comment comment = new Comment();
        //      comment.setContent("damn");
        //      Comment comment1 = new Comment();
        //      comment1.setContent("lmao");
        //
        //      user.setComments(List.of(comment, comment1));
        //
        //      userDao.create(user);

        User user = userDao.get(1L);
        userDao.remove(user);
    }
}
