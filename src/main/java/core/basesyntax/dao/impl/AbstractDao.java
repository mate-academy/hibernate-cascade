package core.basesyntax.dao.impl;

import jakarta.persistence.EntityManagerFactory;

public abstract class AbstractDao {
    protected final EntityManagerFactory entityManagerFactory;

    protected AbstractDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
}
