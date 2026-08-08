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
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Smile entity: " + entity, e);
        }
    }

    @Override
    public Smile get(Long id) {
        try (var session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve Smile entity by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        final String query = "FROM Smile";
        try (var session = factory.openSession()) {
            return session.createQuery(query, Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Smile entities", e);
        }
    }
}
