package core.basesyntax;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = initSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory initSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            StandardServiceRegistryBuilder serviceRegistryBuilder
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            return configuration.buildSessionFactory(serviceRegistryBuilder.build());
        } catch (Exception e) {
            throw new RuntimeException("Can't create session factory ", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
