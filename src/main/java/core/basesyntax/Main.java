package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.MessageDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.Smile;
import java.time.LocalDateTime;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        Smile smileFancy = new Smile("fancy smile");
        Smile smileSad = new Smile("sad smile");
        smileDao.create(smileFancy);
        smileDao.create(smileSad);

        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        Comment comment = new Comment();
        comment.setContent("bla-bla");
        comment.setSmiles(smileDao.getAll());
        commentDao.create(comment);

        commentDao.remove(comment);

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setSender("bob");
        messageDetails.setSentTime(LocalDateTime.now());

        Message message = new Message();
        message.setContent("greate weather");
        message.setMessageDetails(messageDetails);

        MessageDao messageDao = new MessageDaoImpl(sessionFactory);
        messageDao.create(message);

        System.out.println(messageDao.get(1L));
        messageDao.remove(message);

    }
}
