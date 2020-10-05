package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.exceptions.DataProcessingException;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao<Smile> implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile get(Long id) {
        Smile smile;
        try (Session session = factory.openSession()) {
            smile = session.get(Smile.class, id);
            return smile;
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving smile. ", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getAllSmilesQuery = session.createQuery("from Smile", Smile.class);
            return getAllSmilesQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving all smiles. ", e);
        }
    }
}
