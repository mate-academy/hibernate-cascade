package core.basesyntax;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;

import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.Before;

public abstract class AbstractTest {
    protected interface DataSourceProvider {

        enum IdentifierStrategy {
            IDENTITY,
            SEQUENCE
        }

        enum Database {
            HSQLDB,
        }

        String hibernateDialect();

        DataSource dataSource();

        Class<? extends DataSource> dataSourceClassName();

        Properties dataSourceProperties();

        List<IdentifierStrategy> identifierStrategies();

        Database database();
    }

    private SessionFactory factory;

    @Before
    public void init() {
        factory = newSessionFactory();
    }


    private SessionFactory newSessionFactory() {
        Properties properties = getProperties();
        Configuration configuration = new Configuration().addProperties(properties);
        for(Class<?> entityClass : entities()) {
            configuration.addAnnotatedClass(entityClass);
        }
        String[] packages = packages();
        if(packages != null) {
            for(String scannedPackage : packages) {
                configuration.addPackage(scannedPackage);
            }
        }
        Interceptor interceptor = interceptor();
        if(interceptor != null) {
            configuration.setInterceptor(interceptor);
        }
        return configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder()
                        .applySettings(properties)
                        .build()
        );
    }

    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", getDataSourceProvider().hibernateDialect());
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        // log settings
        // properties.put("hibernate.show_sql", Boolean.TRUE.toString());
        // properties.put("hibernate.format_sql", Boolean.TRUE.toString());
        // properties.put("hibernate.use_sql_comments", Boolean.FALSE.toString());

        // data source settings
        properties.put("hibernate.connection.datasource", newDataSource());
        return properties;
    }

    protected DataSource newDataSource() {
        return getDataSourceProvider().dataSource();
    }

    protected abstract Class<?>[] entities();

    protected String[] packages() {
        return null;
    }

    protected Interceptor interceptor() {
        return null;
    }

    protected DataSourceProvider getDataSourceProvider() {
        return new HsqldbDataSourceProvider();
    }

    public SessionFactory getSessionFactory() {
        return factory;
    }

    public static class HsqldbDataSourceProvider implements DataSourceProvider {

        @Override
        public String hibernateDialect() {
            return "org.hibernate.dialect.HSQLDialect";
        }

        @Override
        public DataSource dataSource() {
            JDBCDataSource dataSource = new JDBCDataSource();
            dataSource.setUrl("jdbc:hsqldb:mem:test");
            dataSource.setUser("sa");
            dataSource.setPassword("");
            return dataSource;
        }

        @Override
        public Class<? extends DataSource> dataSourceClassName() {
            return JDBCDataSource.class;
        }

        @Override
        public Properties dataSourceProperties() {
            Properties properties = new Properties();
            properties.setProperty("url", "jdbc:hsqldb:mem:test");
            properties.setProperty("user", "sa");
            properties.setProperty("password", "");
            return properties;
        }

        @Override
        public List<IdentifierStrategy> identifierStrategies() {
            return Arrays.asList(IdentifierStrategy.IDENTITY, IdentifierStrategy.SEQUENCE);
        }

        @Override
        public Database database() {
            return Database.HSQLDB;
        }
    }
}
