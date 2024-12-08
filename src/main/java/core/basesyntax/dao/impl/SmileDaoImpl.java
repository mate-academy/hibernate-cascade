package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
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

            throw new RuntimeException("Cannot create smile", e);
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
            throw new RuntimeException(
                    "Cannot get smile by id: " + id,
                    e
            );
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Smile> criteria = builder.createQuery(Smile.class);

            criteria.from(Smile.class);

            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cannot get smiles", e);
        }
    }
}
