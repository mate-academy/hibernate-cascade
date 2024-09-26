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
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public static void main(String[] args) {
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        UserDao userDao = new UserDaoImpl(sessionFactory);
        User user = userDao.get(1L);
        userDao.remove(user);
    }
}
