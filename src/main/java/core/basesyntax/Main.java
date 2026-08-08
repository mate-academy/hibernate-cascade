package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        UserDao userDao = new UserDaoImpl(sessionFactory);
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);

    }
}
