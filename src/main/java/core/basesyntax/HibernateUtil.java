package core.basesyntax;

import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = initSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory initSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Comment.class);
            configuration.addAnnotatedClass(Smile.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Message.class);
            configuration.addAnnotatedClass(MessageDetails.class);
            StandardServiceRegistryBuilder builder
                    = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties());
            return configuration.buildSessionFactory(builder.build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
