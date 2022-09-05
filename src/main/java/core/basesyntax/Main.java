package core.basesyntax;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = null;

        User user = new User();
        user.setUsername("Oleg");

        User user1 = userDao.create(user);

        System.out.println(user1);
    }
}
