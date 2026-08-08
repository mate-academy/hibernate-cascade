package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create smile: " + entity, e);
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = factory.openSession();
        return session.get(Smile.class, id);
    }

    @Override
    public List<Smile> getAll() {
        Session session = factory.openSession();
        Query<Smile> getAllSmilesQuery = session.createQuery("FROM Smile", Smile.class);
        return getAllSmilesQuery.getResultList();
    }
}
