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
        // use this session factory when you will initialize service instances
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);

        Smile smile1 = new Smile("smile1");
        smileDao.create(smile1);
        Smile smile2 = new Smile("smile2");
        smileDao.create(smile2);
        Smile smile3 = new Smile("smile3");
        smileDao.create(smile3);

        System.out.println("smile1:");
        System.out.println(smileDao.get(1L));
        List<Smile> smilesAll = smileDao.getAll();
        System.out.println("smilesAll:");
        System.out.println(smilesAll);
        System.out.println();

        CommentDao commentDao = new CommentDaoImpl(sessionFactory);

        Comment comment1 = new Comment();
        comment1.setContent("comment1 content");
        comment1.setSmiles(List.of(smile1,smile2));
        commentDao.create(comment1);
        System.out.println("comment1:");
        System.out.println(comment1);
        System.out.println();

        commentDao.remove(comment1);
        System.out.println("\nAfter remove comment1:");
        System.out.println(comment1);
        System.out.println();

        System.out.println("commentsAll:");
        System.out.println(commentDao.getAll());
        System.out.println();

        System.out.println("smilesAll:");
        System.out.println(smileDao.getAll());
        System.out.println();

        System.out.println("PreparingUser:");

        Comment comment2 = new Comment();
        comment2.setContent("comment2 content");
        comment2.setSmiles(List.of(smile2, smile3));
        System.out.println("comment2:");
        System.out.println(comment2);
        System.out.println();

        System.out.println("commentsAll:");
        System.out.println(commentDao.getAll());
        System.out.println();

        User user1 = new User();
        user1.setUsername("user1");
        user1.setComments(List.of(comment2));

        System.out.println("user1:");
        System.out.println(user1);
        System.out.println();

        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user1);

        System.out.println("\nAfter create in DB user1 :");
        System.out.println(user1);
        System.out.println();

        System.out.println("usersAll:");
        System.out.println(userDao.getAll());
        System.out.println();

        System.out.println("commentsAll:");
        System.out.println(commentDao.getAll());
        System.out.println();

        System.out.println("smilesAll:");
        System.out.println(smileDao.getAll());
        System.out.println();

        userDao.remove(user1);

        System.out.println("\nAfter remove in DB user1 :");
        System.out.println(user1);
        System.out.println();

        System.out.println("usersAll:");
        System.out.println(userDao.getAll());
        System.out.println();

        System.out.println("commentsAll:");
        System.out.println(commentDao.getAll());
        System.out.println();

        System.out.println("smilesAll:");
        System.out.println(smileDao.getAll());
        System.out.println();

        System.out.println("\nPreparing message:");

        MessageDetails messageDetails1 = new MessageDetails();
        messageDetails1.setSender("user1");
        messageDetails1.setSentTime(LocalDateTime.now());

        System.out.println("messageDetails1:");
        System.out.println(messageDetails1);
        System.out.println();

        Message message1 = new Message();
        message1.setContent("message1 content");
        message1.setMessageDetails(messageDetails1);

        System.out.println("message1:");
        System.out.println(message1);
        System.out.println();

        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        messageDao.create(message1);

        System.out.println("\nAfter create message1 in DB");
        System.out.println("message1:");
        System.out.println(messageDao.get(1L));
        System.out.println();

        System.out.println("messageAll:");
        System.out.println(messageDao.getAll());
        System.out.println();

        System.out.println("\nAfter remove message1 in DB");

        messageDao.remove(message1);

        System.out.println("messageAll:");
        System.out.println(messageDao.getAll());
        System.out.println();
    }
}
