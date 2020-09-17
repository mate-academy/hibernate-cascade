package core.basesyntax.dao.impl;

import org.hibernate.SessionFactory;

public abstract class AbstractDao {
    protected final SessionFactory sf;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.sf = sessionFactory;
    }
}
