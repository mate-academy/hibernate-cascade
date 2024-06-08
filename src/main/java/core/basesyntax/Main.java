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
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        Smile smile1 = new Smile();
        smile1.setValue("Smile 1");
        smileDao.create(smile1);
        Smile smile1FromDB = smileDao.get(1L);

        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        Comment comment1 = new Comment();
        comment1.setContent("Some content 1");
        comment1.setSmiles(List.of(smile1FromDB));
        commentDao.create(comment1);

        Comment comment2 = new Comment();
        comment2.setContent("Some content 2");
        comment2.setSmiles(List.of(smile1FromDB));
        commentDao.create(comment2);

        Comment comment3 = new Comment();
        comment3.setContent("Some content 3");
        comment3.setSmiles(List.of(smile1FromDB));
        commentDao.create(comment3);

        commentDao.remove(commentDao.get(2L));

        Comment com1FromDB = commentDao.get(1L);
        Comment com3FromDB = commentDao.get(3L);
        UserDao userDao = new UserDaoImpl(sessionFactory);
        User user1 = new User();
        user1.setUsername("Name 1");
        user1.setComments(List.of(com1FromDB, com3FromDB));
        userDao.create(user1);
        userDao.remove(userDao.get(1L));

        Message message = new Message();
        message.setContent("Content 1");
        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("sender 1");
        messageDetails.setSentTime(LocalDateTime.now());
        MessageDetailsDao messageDetailsDao = new MessageDetailsDaoImpl(sessionFactory);
        MessageDetails messageDetails1 = messageDetailsDao.create(messageDetails);
        message.setMessageDetails(messageDetails1);
        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        messageDao.create(message);
        messageDao.remove(messageDao.get(1L));
    }
}
