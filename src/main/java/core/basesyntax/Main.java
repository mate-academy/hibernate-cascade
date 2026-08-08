package core.basesyntax;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.User;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Artem");
        messageDetails.setSentTime(LocalDateTime.now());

        Message message = new Message();
        message.setContent("Comment1");
        message.setMessageDetails(messageDetails);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        messageDao.create(message);
        messageDao.remove(message);

        User user = new User();
        Comment comment = new Comment();
        comment.setContent("111");
        user.setComments(List.of(comment));
        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);
        userDao.get(1L);
    }
}
