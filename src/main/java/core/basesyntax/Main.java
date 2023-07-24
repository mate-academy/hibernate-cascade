package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.*;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public static void main(String[] args) {

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        UserDao userDao = new UserDaoImpl(sessionFactory);
        MessageDaoImpl messageDao = new MessageDaoImpl(sessionFactory);
        Smile smile = smileDao.create(new Smile("funny"));
        Smile smile1 = smileDao.create(new Smile("disappointed"));
        Smile smile2 = smileDao.create(new Smile("relieved"));
        Smile smile3 = smileDao.create(new Smile("sad"));

        Comment comment = new Comment();
        comment.setContent("Some content");
        comment.setSmiles(List.of(smile1, smile2));
        Comment createdComment = commentDao.create(comment);

        User user = new User();
        user.setUsername("Bob");
        Comment userComment = new Comment();
        userComment.setContent("User comment");
        userComment.setSmiles(List.of(smile, smile3));
        user.setComments(List.of(userComment));
        User user1 = userDao.create(user);
        userDao.remove(user1);

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Sender");
        messageDetails.setSentTime(LocalDateTime.now());
        Message message = new Message();
        message.setMessageDetails(messageDetails);
        message.setContent("Message content");
        Message message1 = messageDao.create(message);
        System.out.println(messageDao.getAll());
        System.out.println(smileDao.getAll());
        System.out.println(commentDao.getAll());


    }
}
