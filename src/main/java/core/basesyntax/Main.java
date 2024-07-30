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
import org.hibernate.SessionFactory;

public class Main {
    private static final Long num = 1L;

    public static void main(String[] args) {
        final SessionFactory factory = HibernateUtil.getSessionFactory();
        final SmileDao smileDao = new SmileDaoImpl(factory);
        final CommentDao commentDao = new CommentDaoImpl(factory);
        final UserDao userDao = new UserDaoImpl(factory);
        final MessageDao messageDao = new MessageDaoImpl(factory);
        final MessageDetailsDao messageDetailsDao = new MessageDetailsDaoImpl(factory);

        Smile smile = new Smile();
        smile.setValue(":)");
        smileDao.create(smile);
        System.out.println("smile = " + smileDao.get(num));

        Comment comment = new Comment();
        comment.setContent("I like it!");
        comment.setSmiles(smileDao.getAll());
        commentDao.create(comment);
        System.out.println("comment = " + commentDao.get(num));

        User user = new User();
        user.setUsername("Bob");
        user.setComments(commentDao.getAll());
        userDao.create(user);
        System.out.println("users = " + userDao.getAll());

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender(userDao.get(num).getUsername());
        messageDetails.setSentTime(LocalDateTime.now());
        messageDetailsDao.create(messageDetails);

        Message message = new Message();
        message.setContent("Hello!");
        message.setMessageDetails(messageDetailsDao.get(num));
        messageDao.create(message);
        System.out.println("message = " + messageDao.get(num));
        System.out.println("messages = " + messageDao.getAll());

        userDao.remove(user);
        messageDao.remove(message);
        commentDao.remove(comment);
    }
}
