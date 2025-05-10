package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.dao.impl.MessageDetailsDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Smile smile = new Smile();
        smile.setValue("smile");

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        System.out.println(smileDao.create(smile));
        System.out.println(smileDao.getAll());

        Comment jonCommentOne = new Comment();
        jonCommentOne.setContent("Hi!");
        jonCommentOne.setSmiles(List.of(smile));

        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        System.out.println(commentDao.create(jonCommentOne));
        System.out.println(commentDao.getAll());

        User user = new User();
        user.setUsername("Jon");
        user.setComments(List.of(new Comment()));

        UserDao userDao = new UserDaoImpl(sessionFactory);
        System.out.println(userDao.create(user));
        System.out.println(userDao.getAll());

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSentTime(LocalDateTime.now());
        messageDetails.setSender("Jon");

        MessageDetailsDao messageDetailsDao = new MessageDetailsDaoImpl(sessionFactory);
        messageDetailsDao.create(messageDetails);
        System.out.println(messageDetailsDao.get(1L));

        Message message = new Message();
        message.setContent("message");
        message.setMessageDetails(new MessageDetails());

        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        System.out.println(messageDao.create(message));
        System.out.println(messageDao.getAll());
    }
}
