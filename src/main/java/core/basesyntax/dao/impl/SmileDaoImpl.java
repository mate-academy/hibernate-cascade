package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private static final String CANT_CREATE_MSG = "Can't create smile entity: ";
    private static final String CANT_GET_BY_ID_MSG = "Can't get smile by id: ";
    private static final String CANT_GET_ALL_MSG = "Can't get all smiles";
    private static final String SELECT_ALL_QUERY = "SELECT a FROM Smile a";

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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(CANT_CREATE_MSG+ entity, e);
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
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException(CANT_GET_BY_ID_MSG + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(CANT_GET_ALL_MSG, e);
        }
    }
}
