package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
            throw new RuntimeException("Can't save "
                    + entity.toString() + "to DB", e);
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
            return session.get(Smile.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile with id:"
                    + id.toString() + "from DB", e);
        }
    }

    @Override
    public List<Smile> getAll() {

        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Smile> cq = cb.createQuery(Smile.class);
            Root<Smile> rootEntry = cq.from(Smile.class);
            CriteriaQuery<Smile> all = cq.select(rootEntry);
            TypedQuery<Smile> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't fetch all Smile from DB", e);
        }
    }
}
