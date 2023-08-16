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
    public static void main(String[] argv) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        final UserDao userDao = new UserDaoImpl(sessionFactory);
        final CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        final SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        final MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        final MessageDetailsDao messageDetailsDao = new MessageDetailsDaoImpl(sessionFactory);

        Smile smile = new Smile();
        smile.setValue("fun");
        Smile smile1 = new Smile();
        smile1.setValue("relax");
        Smile smile2 = new Smile();
        smile2.setValue("angry");
        Smile smile3 = new Smile();
        smile3.setValue("cry");

        smileDao.create(smile);
        smileDao.create(smile1);
        smileDao.create(smile2);
        smileDao.create(smile3);

        Comment comment = new Comment();
        comment.setContent("I am fun and relax");
        comment.setSmiles(List.of(smile, smile1));
        Comment comment1 = new Comment();
        comment1.setContent("I am angry and cry");
        comment1.setSmiles(List.of(smile2, smile3));
        User user = new User();
        user.setUsername("confused2224");
        user.setComments(List.of(comment, comment1));

        Comment comment3 = new Comment();
        comment3.setContent("I am fine");
        comment3.setSmiles(List.of(smile, smile1));
        Comment comment4 = new Comment();
        comment4.setContent("I am cry");
        comment4.setSmiles(List.of(smile2, smile3));
        User user1 = new User();
        user1.setUsername("satlan2224");
        user1.setComments(List.of(comment3, comment4));

        userDao.create(user);
        userDao.create(user1);
        User userFromDb = userDao.get(1L);
        String usernameFirstUser = userFromDb.getUsername();
        System.out.println("User with id: " + userFromDb.getId()
                + " and username is: " + usernameFirstUser);
        commentDao.remove(comment);
        userDao.remove(user);

        Message message = new Message();
        message.setContent("Message from Confused!");
        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender(user.getUsername());
        messageDetails.setSentTime(LocalDateTime.now());
        message.setMessageDetails(messageDetails);
        messageDao.create(message);
        String senderFromMessage = messageDao.get(1L).getMessageDetails().getSender();
        System.out.println("Message details sender is: " + senderFromMessage);
        messageDao.remove(message);

        Message message1 = new Message();
        message1.setContent("Message from Satlan!");
        MessageDetails messageDetails1 = new MessageDetails();
        messageDetails1.setSender(user1.getUsername());
        messageDetails1.setSentTime(LocalDateTime.now());
        message1.setMessageDetails(messageDetails1);
        messageDao.create(message1);
        MessageDetails messageDetails2 = messageDetailsDao.get(2L);
        System.out.println("Message details id 2 is : " + messageDetails2);

        List<User> allUsers = userDao.getAll();
        List<Comment> allComments = commentDao.getAll();
        List<Smile> allSmile = smileDao.getAll();
        List<Message> allMessages = messageDao.getAll();
        System.out.println(allUsers);
        System.out.println(allComments);
        System.out.println(allSmile);
        System.out.println(allMessages);
    }
}
