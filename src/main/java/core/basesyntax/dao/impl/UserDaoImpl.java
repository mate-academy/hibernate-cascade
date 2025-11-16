package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public User create(User entity) {
        return persistEntity(entity);
    }

    @Override
    public User get(Long id) {
        return findById(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return getEntities(User.class);
    }

    @Override
    public void remove(User entity) {

        removeEntity(entity);
    }
}
