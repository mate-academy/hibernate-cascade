package core.basesyntax.dao.impl;

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
        Session session = null;
        try {
            session = factory.openSession();
            session.save(entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Can't create new smile: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.find(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile by ID: " + id, e);
        }
    }
    
    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession();) {
            return session.createQuery("from Smile", Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles.", e);
        }
    }
}
