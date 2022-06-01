package core.basesyntax.dao.impl;

import javax.persistence.EntityManagerFactory;

public abstract class AbstractDao {
    protected final EntityManagerFactory factory;

    protected AbstractDao(EntityManagerFactory factory) {
        this.factory = factory;
    }
}
