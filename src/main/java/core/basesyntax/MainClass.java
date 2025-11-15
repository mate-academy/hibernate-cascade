package core.basesyntax;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import org.hibernate.SessionFactory;
import java.time.LocalDateTime;

public class MainClass {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("John12");
        messageDetails.setSentTime(LocalDateTime.now());

        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        Message message = new Message();
        message.setContent("Hello World!!!");
        message.setMessageDetails(messageDetails);

        messageDao.create(message);

        messageDao.remove(message);
    }

}
