package core.basesyntax.service.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import core.basesyntax.service.UserService;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao dao;
    
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }
    
    @Override
    public User create(User entity) {
        return dao.create(entity);
    }
    
    @Override
    public User get(Long id) {
        return dao.get(id);
    }
    
    @Override
    public List<User> getAll() {
        return dao.getAll();
    }
    
    @Override
    public void remove(User entity) {
        dao.remove(entity);
    }
}
