package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

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
            throw new RuntimeException("Can't save Smile to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        session.close();
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Smile from database ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Smile> criteria = builder.createQuery(Smile.class);
            Root<Smile> root = criteria.from(Smile.class);
            criteria.select(root);
            Query<Smile> query = session.createQuery(criteria);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get Smiles from database ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
