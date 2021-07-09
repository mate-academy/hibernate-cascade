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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Smile happy = new Smile();
        happy.setValue("Happy");

        Smile sad = new Smile();
        sad.setValue("Sad");
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        smileDao.create(happy);
        smileDao.create(sad);
        System.out.println(smileDao.get(1L));
        System.out.println(smileDao.get(2L));

        Comment comment = new Comment();
        comment.setContent("Hello world");
        comment.setSmiles(List.of(happy));

        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        commentDao.create(comment);
        System.out.println(commentDao.get(1L));
        commentDao.remove(comment);

        User user = new User();
        user.setUsername("First User");
        Comment usersComment = new Comment();
        usersComment.setSmiles(List.of(sad));
        usersComment.setContent("I am first user");
        user.setComments(List.of(usersComment));

        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);
        System.out.println(userDao.get(user.getId()));
        userDao.remove(user);

        Message message = new Message();
        message.setContent("Hello world");
        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Me");
        messageDetails.setSentTime(LocalDateTime.now());
        message.setMessageDetails(List.of(messageDetails));

        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        messageDao.create(message);
        System.out.println(messageDao.get(1L));
        messageDao.remove(message);
    }
}
