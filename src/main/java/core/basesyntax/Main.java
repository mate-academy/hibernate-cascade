package core.basesyntax;

import core.basesyntax.dao.GenericDao;
import core.basesyntax.dao.SmileDao;
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
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);

        Smile smile1 = new Smile();
        smile1.setValue("Happy");
        smileDao.create(smile1);

        Smile smile2 = new Smile();
        smile2.setValue("Sad");
        smileDao.create(smile2);

        smileDao.get(1L);
        smileDao.get(2L);

        GenericDao<Comment> commentDao = new CommentDaoImpl(sessionFactory);
        Comment comment = new Comment();
        comment.setSmiles(List.of(smile1, smile2));
        commentDao.create(comment);

        List<Comment> allComments = commentDao.getAll();
        commentDao.remove(comment);
        System.out.println(allComments);

        GenericDao<User> userDao = new UserDaoImpl(sessionFactory);
        User user = new User();
        Comment userComment = new Comment();
        userComment.setContent("Hello!");
        user.setComments(List.of(userComment));
        userDao.create(user);

        List<User> allUsers = userDao.getAll();
        userDao.remove(user);
        System.out.println(allUsers);

        GenericDao<Message> messageDao = new MessageDaoImpl(sessionFactory);
        Message message = new Message();
        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("Bob");
        message.setMessageDetails(messageDetails);
        messageDao.create(message);

        messageDao.remove(message);
    }
}
