package core.basesyntax.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
    private static final EntityManagerFactory entityManagerFactory = initEntityManagerFactory();

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    private static EntityManagerFactory initEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("core.basesyntax.ticket_app");
    }
}
