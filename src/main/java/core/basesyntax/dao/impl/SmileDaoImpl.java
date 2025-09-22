package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
        Transaction tx = null;
        try (Session session = super.factory.openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public Smile get(Long id) {
        Transaction tx = null;
        try (Session session = super.factory.openSession()) {
            tx = session.beginTransaction();
            Smile smile = session.get(Smile.class, id);
            tx.commit();
            return smile;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = super.factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Smile> cq = cb.createQuery(Smile.class);
            Root<Smile> smile = cq.from(Smile.class);
            CriteriaQuery<Smile> all = cq.select(smile);
            TypedQuery<Smile> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
