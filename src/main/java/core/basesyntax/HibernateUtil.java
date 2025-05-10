package core.basesyntax;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = initSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory initSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception exception) {
            throw new RuntimeException("Can't create session factory ", exception);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
