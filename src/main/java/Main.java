import core.basesyntax.HibernateUtil;
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

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        Smile dot = new Smile(".");
        smileDao.create(dot);
        Smile happySmile = new Smile(":)");
        smileDao.create(happySmile);
        Smile sadSmile = new Smile(":(");
        smileDao.create(sadSmile);

        Comment comment = new Comment();
        comment.setContent("Some comment text!");
        comment.setSmiles(List.of(happySmile, sadSmile, dot));
        Comment otherComment = new Comment();
        otherComment.setContent("Some otherComment text!");
        otherComment.setSmiles(List.of(happySmile, sadSmile, dot));
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        commentDao.create(comment);
        commentDao.create(otherComment);
        commentDao.remove(otherComment);
        commentDao.remove(comment);

        Comment comment1 = new Comment();
        comment1.setContent("Some comment text 1!!!!!!");
        comment1.setSmiles(List.of( dot));
        Comment comment2 = new Comment();
        comment2.setContent("Some comment text 2!!!!!");
        comment2.setSmiles(List.of(happySmile, dot));
        User user = new User();
        user.setUsername("User-1");
        user.setComments(List.of(comment1, comment2));
        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);
        userDao.remove(user);

        MessageDetails messageDetails1 = new MessageDetails();
        messageDetails1.setSender("Andrey");
        messageDetails1.setSentTime(LocalDateTime.now());
        MessageDetails messageDetails2 = new MessageDetails();
        messageDetails2.setSender("Maria");
        messageDetails2.setSentTime(LocalDateTime.now());
        Message message = new Message();
        message.setContent("Message context");
        message.setMessageDetails(List.of(messageDetails1, messageDetails2));
        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        messageDao.create(message);
    }
}
