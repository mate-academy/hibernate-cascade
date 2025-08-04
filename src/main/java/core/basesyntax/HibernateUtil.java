package core.basesyntax;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory instance = initSessionFactory();

    private static SessionFactory initSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return instance;
    }
//    private static SessionFactory sessionFactory = initSessionFactory();
//
//    private HibernateUtil() {
//    }
//
//    private static SessionFactory initSessionFactory() {
//        try {
//            return new Configuration().configure().buildSessionFactory();
//        } catch (Exception e) {
//            throw new RuntimeException("Can't create session factory ", e);
//        }
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
}
