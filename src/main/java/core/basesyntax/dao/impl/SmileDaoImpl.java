package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
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
        try {
            Session session = sf.openSession();
            transaction = session.beginTransaction();
            session.persist(smile);
            transaction.commit();
            return smile;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Smile entity", e);
        }
    }

    @Override
    public Smile get(Long id) {
        return sf.openSession().get(Smile.class, id);
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = sf.openSession()) {
            CriteriaQuery<Smile> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Smile.class);
            criteriaQuery.from(Smile.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
