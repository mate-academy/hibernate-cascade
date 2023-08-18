package core.basesyntax;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static EntityManagerFactory entityManagerFactory = initEntityManagerFactory();

    private HibernateUtil() {
    }

    private static EntityManagerFactory initEntityManagerFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Can't create session factory ", e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
