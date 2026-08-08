package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Smile entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            if (id == null) {
                throw new IllegalArgumentException("Id cannot be null");
            }
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Smile entity by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        String query = "SELECT * FROM smiles";
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(query, Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all Smile entities", e);
        }
    }
}
