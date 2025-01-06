package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.SessionFactory;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Smile get(Long id) {
        try (var session = factory.openSession()) {
            return session.get(Smile.class, id);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (var session = factory.openSession()) {
            return session.createQuery("from Smile", Smile.class).list();
        }
    }
}

