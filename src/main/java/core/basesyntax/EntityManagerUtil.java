package core.basesyntax;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
    private static EntityManagerFactory entityManagerFactory = initEntryManagerFactory();

    private static EntityManagerFactory initEntryManagerFactory() {
        return Persistence.createEntityManagerFactory("core.basesyntax.ticket_app");
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
