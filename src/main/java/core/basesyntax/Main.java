package core.basesyntax;

import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UserDaoImpl userDao = new UserDaoImpl(sessionFactory);
        User user = new User();
        user.setUsername("Tolik");
        Comment comment = new Comment();
        comment.setContent("Privet");
        user.setComments(List.of(comment));
        userDao.create(user);
        userDao.remove(user);
        System.out.println(userDao.getAll());

    }
}
