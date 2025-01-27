package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import org.hibernate.SessionFactory;

import java.util.List;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        return executeInsideTransaction(session -> {
            session.persist(entity);
            return entity;
        });
    }

    @Override
    public Smile get(Long id) {
        return executeInsideTransaction(session -> session.get(Smile.class, id));
    }

    @Override
    public List<Smile> getAll() {
        return executeInsideTransaction(session ->
                session.createQuery("FROM Smile", Smile.class).list());
    }
}
