package core.basesyntax;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = initSessionFactory();

    private static SessionFactory initSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
