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

    private final SessionFactory sessionFactory;

    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Smile create(Smile entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Saving Smile to Database Error, cause: "
                    + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Smile.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Loading all Smiles from Database Error, cause: "
                    + e.getMessage(), e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Smile> criteria = builder.createQuery(Smile.class);
            criteria.from(Smile.class);
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Loading Smile from Database Error, cause: "
                    + e.getMessage(), e);
        }
    }
}
