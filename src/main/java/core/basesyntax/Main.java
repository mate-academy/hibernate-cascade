package core.basesyntax;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Smile happySmail = new Smile();
        happySmail.setValue("happy");

        Smile treeSmail = new Smile();
        treeSmail.setValue("tree");

        //create new smiles
        SmileDao smileDao = new SmileDaoImpl(sessionFactory);
        smileDao.create(happySmail);
        smileDao.create(treeSmail);

        //create a new comment
        Comment comment = new Comment();
        comment.setContent("Happy New Year");
        comment.setSmiles(List.of(happySmail, treeSmail));

        //creat a nw user
        User user = new User();
        user.setUsername("Daryna");
        user.setComments(List.of(comment));
        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);

    }
}
