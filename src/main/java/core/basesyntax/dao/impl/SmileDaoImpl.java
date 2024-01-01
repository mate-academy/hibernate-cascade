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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create smile by id " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            Smile smile = session.get(Smile.class, id);
            if (smile != null) {
                return smile;
            } else {
                throw new RuntimeException("Can't get smile by id " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile by id " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Smile> criteriaQuery = criteriaBuilder.createQuery(Smile.class);
            Root<Smile> root = criteriaQuery.from(Smile.class);
            CriteriaQuery<Smile> all = criteriaQuery.select(root);
            TypedQuery<Smile> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile list", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
