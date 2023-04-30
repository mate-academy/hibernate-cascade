package core.basesyntax.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
    private static final EntityManagerFactory entityManagerFactory = initEntityManagerFactory();

    private static EntityManagerFactory initEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("mate.academy.social_app");
    }


    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
