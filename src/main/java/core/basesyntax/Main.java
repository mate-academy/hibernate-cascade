package core.basesyntax;

import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.User;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        UserDaoImpl userDao = new UserDaoImpl(sessionFactory);
        User user = new User();
        user.setUsername("hello");
//        user.set("hello"/);
        System.out.println(userDao.create(user));
    }
}
