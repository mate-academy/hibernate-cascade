import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.User;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("Alice");
        UserDao userDao = new UserDaoImpl(HibernateUtil.getSessionFactory());
        userDao.create(user);
    }
}
