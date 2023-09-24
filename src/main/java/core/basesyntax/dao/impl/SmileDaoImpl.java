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
        try {
            return factory.fromTransaction(session -> {
                session.persist(entity);
                return entity;
            });
        } catch (Exception e) {
            throw new RuntimeException("Can't create Smile: " + entity, e);
        }
    }

    @Override
    public Smile get(Long id) {
        try {
            return factory.fromSession(session -> session.get(Smile.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can't get Smile by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try {
            return factory.fromSession(session -> session.createQuery("FROM Smile", Smile.class)
                    .getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Can't get list of Smiles", e);
        }
    }
}
