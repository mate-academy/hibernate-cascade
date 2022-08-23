package core.basesyntax;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
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
        Smile funny = new Smile();
        Smile sad = new Smile();
        Smile laugh = new Smile();
        sad.setValue("Sad!");
        funny.setValue("Funny!");
        laugh.setValue("Laugh!");

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);

        smileDao.create(funny);
        smileDao.create(sad);
        smileDao.create(laugh);

        User user = new User();
        user.setUsername("Nazar");
        Comment comment = new Comment();
        comment.setContent("Good Bye!");
        comment.setSmiles(List.of(funny, laugh));
        user.setComments(List.of(comment));

        Message message = new Message();
        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Nazar");
        messageDetails.setSentTime(LocalDateTime.now());
        message.setContent("I've recently left my comment");
        message.setMessageDetails(messageDetails);
        MessageDao messageDao = new MessageDaoImpl(sessionFactory);

        messageDao.create(message);
        messageDao.remove(message);

        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);
        userDao.remove(user);
    }
}
