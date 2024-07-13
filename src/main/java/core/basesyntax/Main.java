package core.basesyntax;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.User;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static void main(String[] args) {

        User user = new User();
        user.setUsername("Ivanov Ivan");

        Comment comment = new Comment();
        comment.setContent("content of comment");

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("facebook");
        messageDetails.setSentTime(LocalDateTime.now());

        Message message = new Message();
        message.setContent("some context");
        message.setMessageDetails(messageDetails);
        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        messageDao.create(message);

        List<Message> lists = messageDao.getAll();

        for (Message m : lists) {
            System.out.println(m.getContent() + " - " + m.getMessageDetails().getSender());
        }

    }
}
