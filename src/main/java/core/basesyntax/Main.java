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
import core.basesyntax.util.HibernateUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        Smile smile1 = new Smile();
        smile1.setValue("=)");
        smileDao.create(smile1);

        Smile smile2 = new Smile();
        smile2.setValue("=(");
        smileDao.create(smile2);

        Smile smile3 = new Smile();
        smile3.setValue(";)");
        smileDao.create(smile3);

        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        Comment comment1 = new Comment();
        comment1.setContent("I'm happy!");
        comment1.setSmiles(List.of(smile1, smile2));
        commentDao.create(comment1);

        Comment comment2 = new Comment();
        comment2.setContent("I'm sad!");
        comment2.setSmiles(List.of(smile3));
        commentDao.create(comment2);

        MessageDetailsDao detailsDao = new MessageDetailsDaoImpl(sessionFactory);
        MessageDetails details1 = new MessageDetails();
        details1.setSender("Spenser");
        details1.setSentTime(LocalDateTime.now());
        detailsDao.create(details1);

        MessageDetails details2 = new MessageDetails();
        details2.setSender("Veronica");
        details2.setSentTime(LocalDateTime.now());
        detailsDao.create(details2);

        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        Message message = new Message();
        message.setContent("My friends!");
        message.setMessageDetails(List.of(details1, details2));
        messageDao.create(message);

        UserDao userDao = new UserDaoImpl(sessionFactory);
        User user = new User();
        user.setUsername("Dima");
        user.setComments(List.of(comment1, comment2));
        userDao.create(user);

        System.out.println(userDao.getAll());
        System.out.println(messageDao.getAll());
        System.out.println(commentDao.getAll());

        userDao.remove(user);

    }
}
