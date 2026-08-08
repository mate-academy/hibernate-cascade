package core.basesyntax;

import core.basesyntax.dao.MessageDao;
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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        SmileDaoImpl smileDao = new SmileDaoImpl(sessionFactory);

        Smile smile = new Smile();
        smile.setValue("happy :)");
        Comment comment = new Comment();
        comment.setContent("This is a comment!");
        List<Smile> smiles = List.of(smile);
        for (Smile oneSmile : smiles) {
            oneSmile.setComment(comment);
        }
        comment.setSmiles(smiles);

        UserDao userDao = new UserDaoImpl(sessionFactory);
        User user = new User();
        user.setUsername("John");
        user.setComments(List.of(comment));
        userDao.create(user);

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender(user.getUsername());
        messageDetails.setSentTime(LocalDateTime.now());

        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        Message message = new Message();
        message.setContent("Stay calm!");
        message.setMessageDetails(messageDetails);
        List<Message> allMessages = messageDao.getAll();
        for (Message text : allMessages) {
            System.out.println(text);
        }
        messageDao.create(message);

        User userFromDb = userDao.get(5L);
        userDao.remove(userFromDb);
    }
}
