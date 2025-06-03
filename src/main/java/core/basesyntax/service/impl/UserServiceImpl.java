package core.basesyntax.service.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.User;
import core.basesyntax.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void remove(User user) {
        for (Comment comment : user.getComments()) {
            comment.setUser(null);
        }
        userDao.remove(user);
    }
}
