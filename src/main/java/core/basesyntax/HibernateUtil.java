package core.basesyntax;

import java.io.File;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final Logger LOG = Logger.getLogger(HibernateUtil.class.getName());
    private static SessionFactory sessionFactory = initSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory initSessionFactory() {
        try {
            final String hibernateConfigPath = "/path/to/hibernate.cfg.xml";
            final File hibernateConfigFile = new File(hibernateConfigPath);
            LOG.info("Trying to initialize SessionFactory with config file: "
                    + hibernateConfigPath);

            // Check if the configuration file exists
            if (hibernateConfigFile.exists()) {
                LOG.info("File exists. Initializing with custom file.");
                return new Configuration().configure(hibernateConfigFile).buildSessionFactory();
            } else {
                LOG.info("File doesn't exist. Initializing with default project file.");
                return new Configuration().configure().buildSessionFactory();
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't create session factory ", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
