package core.basesyntax.service.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.lib.Inject;
import core.basesyntax.model.User;
import core.basesyntax.service.UserService;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User entity) {
        return userDao.create(entity);
    }

    @Override
    public User get(Long id) {
        User user = userDao.get(id);
        if (user == null) {
            throw new DataProcessingException("User not found for id: " + id);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userDao.getAll();
        if (users == null) {
            throw new DataProcessingException("User not found for id's");
        }
        return users;
    }

    @Override
    public void remove(User entity) {
        userDao.remove(entity);
    }
}
