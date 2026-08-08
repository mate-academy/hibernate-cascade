package core.basesyntax.dao.impl;

import org.hibernate.SessionFactory;

public abstract class AbstractDao {
    protected final SessionFactory sessionFactory;

    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
