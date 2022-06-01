package core.basesyntax;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
    private static final EntityManagerFactory factoryManager = initEntityManagerFactory();

    private static EntityManagerFactory initEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("cascade-hw");
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return factoryManager;
    }
}
