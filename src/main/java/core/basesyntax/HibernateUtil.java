package core.basesyntax;

import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = initSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory initSessionFactory() {
        try {
            return new Configuration()
                    .configure()
                    .addAnnotatedClass(Comment.class)
                    .addAnnotatedClass(Smile.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Message.class)
                    .addAnnotatedClass(MessageDetails.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Can't create session factory ", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
