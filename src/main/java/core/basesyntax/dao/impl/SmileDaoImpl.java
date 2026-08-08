package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        factory.inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can not get Smile by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        Query<Smile> smileQuery = factory
                .openSession().createQuery("from Smile", Smile.class);
        return smileQuery.getResultList();
    }
}
