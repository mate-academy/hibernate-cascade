package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User get(Long id) {
        try (var session = factory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> getAll() {
        try (var session = factory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void remove(User entity) {
      try(var session = factory.openSession()){
          var transaction = session.beginTransaction();
          session.remove(entity);
          transaction.commit();
      } catch (Exception e){
          e.printStackTrace();
      }
    }
}

