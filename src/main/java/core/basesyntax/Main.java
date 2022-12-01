package core.basesyntax;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.dao.impl.MessageDetailsDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        MessageDetailsDao messageDetailsDao = new MessageDetailsDaoImpl(sessionFactory);
        UserDao userDao = new UserDaoImpl(sessionFactory);
//        MessageDetails md1 = new MessageDetails("Life is life", LocalDateTime.now());
//        MessageDetails md2 = new MessageDetails("Life is good", LocalDateTime.now());
//        MessageDetails md3 = new MessageDetails("Live is bad", LocalDateTime.now());
//        MessageDetails md4 = new MessageDetails("Kosmos", LocalDateTime.now());
//        messageDetailsDao.create(md1);
//        messageDetailsDao.create(md2);
//        messageDetailsDao.create(md3);
//        messageDetailsDao.create(md4);


//        Message arnold = new Message("Hallo word!",messageDetailsArnold);
//        Message silverter = new Message("Hallo word!",messageDetailsSilvester);
//        Message li = new Message("Hallo word!",messageDetailsLi);
//        Message m1 = messageDao.get(3L);

//        Message toDelete = messageDao.get(2L);
//        System.out.println(toDelete);
//        messageDao.remove(toDelete);
//        System.out.println(messageDao.get(2L));

//        MessageDetails one = messageDetailsDao.get(2L);
//        MessageDetails two = messageDetailsDao.get(3L);
//        MessageDetails three = messageDetailsDao.get(4L);
//       messageDetailsDao.remove(one);
//        messageDetailsDao.remove(two);
//        messageDetailsDao.remove(three);


    }
}
