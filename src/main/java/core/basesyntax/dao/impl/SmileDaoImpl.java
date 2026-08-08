package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;
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
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save comment to DB");
        } finally {
            session.close();
        }

        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = super.factory.openSession();
        Optional<Smile> smile = Optional.ofNullable(session.get(Smile.class, id));

        return smile.orElseThrow(() -> new RuntimeException("Such element dasn't exist in DB"));
    }

    @Override
    public List<Smile> getAll() {
        Session session = super.factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Smile> criteriaQuery = criteriaBuilder.createQuery(Smile.class);
        criteriaQuery.from(Smile.class);

        return session.createQuery(criteriaQuery).getResultList();
    }
}
