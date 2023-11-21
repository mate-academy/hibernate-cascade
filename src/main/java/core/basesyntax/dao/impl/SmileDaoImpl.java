package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private static final String CANNOT_CREATE_SMILE_TEMPLATE = "Cannot create smile: ";
    private static final String CANNOT_GET_SMILE_TEMPLATE = "Cannot get smile with id: ";
    private static final String CANNOT_GET_SMILES = "Cannot get smiles";
    private static final String GET_ALL_SMILES_QUERY = "FROM Smile";

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
            throw new RuntimeException(CANNOT_CREATE_SMILE_TEMPLATE + entity, e);
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
            throw new RuntimeException(CANNOT_GET_SMILE_TEMPLATE + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(GET_ALL_SMILES_QUERY, Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(CANNOT_GET_SMILES, e);
        }
    }
}
