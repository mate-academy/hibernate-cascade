package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.Smile;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create smile: " + entity, e);
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = null;
        Smile smile;
        try {
            session = factory.openSession();
            smile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile with id: " + id, e);
        } finally {
            session.close();
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        List<Smile> smiles;
        try {
            session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Smile> criteriaQuery = criteriaBuilder.createQuery(Smile.class);
            Root<Smile> smileRoot = criteriaQuery.from(Smile.class);
            CriteriaQuery<Smile> all = criteriaQuery.select(smileRoot);
            TypedQuery<Smile> allQuery = session.createQuery(all);
            smiles = allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles", e);
        } finally {
            session.close();
        }
        return smiles;
    }
}
