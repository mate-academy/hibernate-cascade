package core.basesyntax.dao.impl;

import core.basesyntax.model.User;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao<User> {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
