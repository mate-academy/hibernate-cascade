package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    private static final SessionFactory sessionFactory =
            HibernateUtil.getSessionFactory();

    public static void main(String[] args) {
        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        Message message = new Message();
        message.setContent("message");
        message.setMessageDetails(List.of(new MessageDetails()));
        messageDao.create(message);
        messageDao.remove(message);

        User user = new User();
        Comment comment = new Comment();
        user.setComments(List.of(comment));
        user.setUsername("username");
        comment.setContent("comment");
        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);
        userDao.remove(user);

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        Smile smile = new Smile();
        smile.setValue("*");
        smileDao.create(smile);
        comment.setSmiles(List.of(smile));
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        commentDao.create(comment);
    }
}
