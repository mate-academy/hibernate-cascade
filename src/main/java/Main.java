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
import core.basesyntax.util.HibernateUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        final SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        final UserDao userDao = new UserDaoImpl(sessionFactory);
        final CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        final MessageDao messageDao = new MessageDaoImpl(sessionFactory);

        Smile smile = new Smile();
        smileDao.create(smile);

        Comment comment = new Comment();
        comment.setContent("hello mate academy");
        comment.setSmiles(List.of(smile));
        commentDao.create(comment);

        User user = new User();
        user.setUsername("Luke");
        user.setComments(List.of(comment));
        userDao.create(user);

        Message message = new Message();
        message.setContent("hello friend");
        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Luke");
        LocalDateTime localDateTime = messageDetails.getSentTime();
        messageDetails.setSentTime(localDateTime);
        message.setMessageDetails(messageDetails);
        messageDao.create(message);
    }
}
