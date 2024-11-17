package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        Smile smileFromDB = smileDao.get(1L);

        Comment comment = new Comment();
        comment.setContent("Comment");
        comment.setSmiles(List.of(smileFromDB));
        CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        commentDao.create(comment);

    }
}
