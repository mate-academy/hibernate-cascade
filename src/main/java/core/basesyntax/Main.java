package core.basesyntax;

import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.User;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UserDao userDao = new UserDaoImpl(sessionFactory);

        Comment comment = new Comment();
        comment.setContent("dsadsad");

        User user = new User();
        user.setUsername("fdasfsd");

        userDao.create(user);

    }
}
