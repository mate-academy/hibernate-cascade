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
        final UserDao userDao = new UserDaoImpl(sessionFactory);
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        final MessageDao messageDao = new MessageDaoImpl(sessionFactory);

        Smile smile1 = new Smile();
        Smile smile2 = new Smile();
        smileDao.create(smile1);
        smileDao.create(smile2);

        User user = new User();
        user.setUsername("User");

        Comment comment = new Comment();
        comment.setContent("Hello world!");
        comment.setSmiles(List.of(smile1, smile2));
        //comment.setUser(user);

        user.setComments(List.of(comment));
        userDao.create(user);

        Message message = new Message();
        message.setContent("Alert!");

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("System");
        messageDetails.setSentTime(LocalDateTime.now());
        message.setMessageDetails(messageDetails);
        messageDao.create(message);
    }
}
