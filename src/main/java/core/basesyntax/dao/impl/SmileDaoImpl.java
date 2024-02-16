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
    public Smile create(Smile entity) {
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = factory;
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save smile " + entity + " to DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile;
        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            sessionFactory = factory;
            session = sessionFactory.openSession();
            smile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile by id " + id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Smile> smileCriteriaQuery = criteriaBuilder.createQuery(Smile.class);
        smileCriteriaQuery.from(Smile.class);
        return session.createQuery(smileCriteriaQuery).getResultList();
    }
}
