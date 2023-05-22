package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile smile) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(smile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Exception while trying to create smile " + smile + " to DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Exception while trying to get smile from DB with id = " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            CriteriaQuery<Smile> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Smile.class);
            criteriaQuery.select(criteriaQuery.from(Smile.class));
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Exception while trying to get all smiles from DB", e);
        }
    }
}
