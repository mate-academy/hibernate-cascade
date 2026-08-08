package core.basesyntax.dao.impl;

import static core.basesyntax.util.HqlQueries.GET_ALL_SMILES;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(GET_ALL_SMILES, Smile.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles", e);
        }
    }
}
