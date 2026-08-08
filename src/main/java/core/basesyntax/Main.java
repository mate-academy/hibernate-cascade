package core.basesyntax;

import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.User;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("Bob");
        UserDao userDao = new UserDaoImpl(HibernateUtil.getSessionFactory());
        userDao.create(user);
    }
}
