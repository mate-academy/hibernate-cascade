package core.basesyntax.dao;

import core.basesyntax.model.User;
import java.util.List;

public interface UserDao extends GenericDao<User> {
    public User create(User entity);

    public User get(Long id);

    public List<User> getAll();

    public void remove(User entity);
}
