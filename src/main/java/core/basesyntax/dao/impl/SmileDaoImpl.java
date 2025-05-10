package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private static final String GET_ALL_QUERY = "SELECT * FROM smile";
    private static final String ADD_EXCEPTION_MESSAGE = "Failed to add smile to DB ";
    private static final String GET_EXCEPTION_MESSAGE = "Failed to get smile from DB by id=";
    private static final String GET_ALL_EXCEPTION_MESSAGE = "Failed to get all smiles from DB";

    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(ADD_EXCEPTION_MESSAGE + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(Smile.class, id))
                        .orElseThrow(() -> new RuntimeException(GET_EXCEPTION_MESSAGE + id));
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(GET_ALL_QUERY, Smile.class).getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException(GET_ALL_EXCEPTION_MESSAGE, e);
        }
    }
}
