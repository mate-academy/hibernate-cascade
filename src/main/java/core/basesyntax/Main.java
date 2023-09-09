package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CommentDao commentDao = new CommentDaoImpl(HibernateUtil.getSessionFactory());
        SmileDao smileDao = new SmileDaoImpl(HibernateUtil.getSessionFactory());
        Comment comment = commentDao.get(3L);
        commentDao.remove(comment);
    }
}
