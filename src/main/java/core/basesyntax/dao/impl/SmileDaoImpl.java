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
        factory.inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public Smile get(Long id) {
        return factory.fromSession(session -> session.get(Smile.class, id));
    }

    @Override
    public List<Smile> getAll() {
        return factory.fromSession(
                session -> session.createQuery("from Smile ", Smile.class)
                        .getResultList());
    }
}
