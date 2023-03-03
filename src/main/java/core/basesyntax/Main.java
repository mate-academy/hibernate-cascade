package core.basesyntax;

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
        SmileDaoImpl smileDao = new SmileDaoImpl(sessionFactory);

        Smile smileHappy = new Smile("Happy");
        Smile smileSad = new Smile("Sad");
        Smile smileAngry = new Smile("Angry");

        smileDao.create(smileHappy);
        smileDao.create(smileAngry);
        smileDao.create(smileSad);

        List<Smile> smileList = smileDao.getAll();
        System.out.println(smileList);

        CommentDaoImpl commentDao = new CommentDaoImpl(sessionFactory);
        Comment commentFirst = new Comment();
        commentFirst.setContent("This is the first comment");
        commentFirst.setSmiles(List.of(smileDao.get(1L), smileDao.get(3L)));
        System.out.println(commentDao.create(commentFirst));
        System.out.println(commentDao.get(1L));

        Comment commentSecond = new Comment();
        commentSecond.setContent("This is the second comment");
        commentSecond.setSmiles(smileDao.getAll());
        System.out.println(commentDao.create(commentSecond));
        System.out.println(commentDao.getAll());
        commentDao.remove(commentDao.get(1L));

        Comment commentFirstOfUserFirst = new Comment();
        commentFirstOfUserFirst.setSmiles(List.of(smileDao.get(2L), smileDao.get(3L)));
        commentFirstOfUserFirst.setContent("This is the first comment of userFirst");

        Comment commentSecondOfUserFirst = new Comment();
        commentSecondOfUserFirst.setSmiles(List.of(smileDao.get(1L), smileDao.get(2L)));
        commentSecondOfUserFirst.setContent("This is the second comment of userFirst");

        User userFirst = new User();
        userFirst.setUsername("Bob");
        userFirst.setComments(List.of(commentFirstOfUserFirst, commentSecondOfUserFirst));
        UserDaoImpl userDao = new UserDaoImpl(sessionFactory);
        System.out.println(userDao.create(userFirst));

        Comment commentFirstOfUserSecond = new Comment();
        commentFirstOfUserSecond.setSmiles(smileDao.getAll());
        commentFirstOfUserSecond.setContent("This is the first comment of userSecond");

        Comment commentSecondOfUserSecond = new Comment();
        commentSecondOfUserSecond.setSmiles(List.of(smileDao.get(2L)));
        commentSecondOfUserSecond.setContent("This is the second comment of userSecond");

        User userSecond = new User();
        userSecond.setUsername("Alice");
        userSecond.setComments(List.of(commentFirstOfUserSecond, commentSecondOfUserSecond));

        System.out.println(userDao.create(userSecond));

        System.out.println(userDao.get(2L));

        userDao.getAll().forEach(System.out::println);

        userDao.remove(userDao.get(2L));

        Message messageFirst = new Message();
        messageFirst.setContent("This is the first message");
        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Bob");
        messageDetails.setSentTime(LocalDateTime.now());
        messageFirst.setMessageDetails(messageDetails);
        MessageDaoImpl messageDao = new MessageDaoImpl(sessionFactory);
        System.out.println(messageDao.create(messageFirst));

        //MessageDaoImpl messageDao = new MessageDaoImpl(sessionFactory);
        Message messageSecond = new Message();
        messageSecond.setContent("This is the second message");
        //MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Alice");
        messageDetails.setSentTime(LocalDateTime.now());
        messageSecond.setMessageDetails(messageDetails);

        System.out.println(messageDao.create(messageSecond));

        System.out.println(messageDao.get(3L));

        messageDao.getAll().forEach(System.out::println);

        messageDao.remove(messageDao.get(4L));

        MessageDetailsDaoImpl messageDetailsDao = new MessageDetailsDaoImpl(sessionFactory);
        System.out.println(messageDetailsDao.get(1L));

    }
}
