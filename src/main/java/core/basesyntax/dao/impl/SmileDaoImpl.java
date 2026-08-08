package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile smile) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(smile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Smile to DB: " + smile);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        Smile dbSmile = null;
        try (Session session = factory.openSession()) {
            dbSmile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Smile by id: " + id);
        }
        return dbSmile;
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        List<Smile> smiles;
        try {
            session = factory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Smile> criteria = builder.createQuery(Smile.class);
            criteria.from(Smile.class);
            smiles = session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all Smiles from DB. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smiles;
    }
}
