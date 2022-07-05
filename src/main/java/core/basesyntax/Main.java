package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        UserDao userDao = new UserDaoImpl(sessionFactory);
        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
    }
}
