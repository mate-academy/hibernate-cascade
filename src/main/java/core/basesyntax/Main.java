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
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        Smile smile1 = new Smile();
        smile1.setValue("Smile");
        Smile smile2 = new Smile();
        smile2.setValue("Angry");
        Smile smile3 = new Smile();
        smile3.setValue("Crying");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        smileDao.create(smile1);
        smileDao.create(smile2);
        smileDao.create(smile3);
        System.out.println(smileDao.get(1L));
        System.out.println(smileDao.getAll());
        Smile nonExistedSmile = new Smile();
        nonExistedSmile.setValue("Non existed");
        Comment comment = new Comment();
        comment.setContent("Hello, i'm first comment");
        comment.setSmiles(List.of(smile1, smile2));
        Comment comment2 = new Comment();
        comment2.setContent("hi, i'm second commentator");
        comment2.setSmiles(List.of(smile2, smile3));
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        commentDao.create(comment);
        commentDao.create(comment2);
        System.out.println(commentDao.get(1L));
        System.out.println(commentDao.getAll().get(0).getContent());
        commentDao.remove(comment);

        UserDao userDao = new UserDaoImpl(sessionFactory);
        User user = new User();
        user.setUsername("TestUser");
        user.setComments(List.of(comment2));
        userDao.create(user);
        User user2 = new User();
        user2.setUsername("SecondUser");
        Comment comment3 = new Comment();
        comment3.setContent("Wow");
        comment3.setSmiles(List.of(smile1, smile3));
        Comment comment4 = new Comment();
        comment4.setContent("lol");
        comment4.setSmiles(List.of(smile1));
        user2.setComments(List.of(comment3, comment4));
        userDao.create(user2);
        userDao.remove(user);

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Bob");
        messageDetails.setSentTime(LocalDateTime
                .of(2000, 4, 21, 12, 0));
        Message message = new Message();
        message.setContent("Hellp");
        message.setMessageDetails(messageDetails);

        MessageDetails messageDetails2 = new MessageDetails();
        messageDetails2.setSender("Kate");
        messageDetails2.setSentTime(LocalDateTime
                .of(2019, 2, 5, 18, 20));
        Message message2 = new Message();
        message2.setContent(";)");
        message2.setMessageDetails(messageDetails2);
        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        messageDao.create(message);
        messageDao.create(message2);
        messageDao.remove(message);

    }
}
