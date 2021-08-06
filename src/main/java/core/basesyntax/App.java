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

public class App {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Smile smileGood = new Smile("Good");
        Smile smileAwesome = new Smile("Awesome");
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        smileDao.create(smileGood);
        smileDao.create(smileAwesome);

        Comment comment = new Comment();
        comment.setContent("Everything was cool");
        comment.setSmiles(List.of(smileGood, smileAwesome));
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        commentDao.create(comment);

        System.out.println(commentDao.getAll());
        System.out.println(commentDao.get(comment.getId()));

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Bob");
        messageDetails.setSentTime(LocalDateTime.of(2021, 6, 2, 13, 10));

        Message message = new Message();
        message.setContent("Hello world");
        message.setMessageDetails(List.of(messageDetails));

        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        messageDao.create(message);
        System.out.println(messageDao.get(message.getId()));
        System.out.println(messageDao.getAll());

        UserDao userDao = new UserDaoImpl(sessionFactory);
        User bob = new User();
        bob.setUsername("Bob");
        userDao.create(bob);

        User alice = new User();
        alice.setUsername("Alice");
        alice.setComments(List.of(new Comment()));
        userDao.create(alice);

        System.out.println(userDao.get(alice.getId()));
        System.out.println(userDao.getAll());
        userDao.remove(bob);
        messageDao.remove(message);
    }
}
