package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private static final String EXCEPTION_CAN_T_CREATE_SMILE_MESSAGE =
            "Can not to create smile with id: ";
    private static final String EXCEPTION_CAN_T_GET_SMILE_MESSAGE =
            "Can not to get smile with id: ";
    private static final String EXCEPTION_CAN_T_GET_ALL_SMILES_MESSAGE =
            "Can not to get all smiles";
    private static final String QUERY_GET_ALL_SMILES = "SELECT * FROM smiles";

    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                return entity;
            } catch (Exception e) {
                transaction.rollback();
            }
            throw new RuntimeException(EXCEPTION_CAN_T_CREATE_SMILE_MESSAGE + entity.getId());
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException(EXCEPTION_CAN_T_GET_SMILE_MESSAGE + id);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(QUERY_GET_ALL_SMILES, Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(EXCEPTION_CAN_T_GET_ALL_SMILES_MESSAGE, e);
        }
    }
}
