package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
            throw new RuntimeException("Link to the smile is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create new entity with a smile in db: " + entity, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        if (id == null) {
            throw new RuntimeException("This 'id' is null");
        }
        Smile smile;
        try (Session session = factory.openSession()) {
            smile = session.get(Smile.class, id);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Can't get entity of smile from db by id = " + id);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smiles;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Smile> query = builder.createQuery(Smile.class);
            Root<Smile> rootEntry = query.from(Smile.class);
            CriteriaQuery<Smile> all = query.select(rootEntry);
            smiles = session.createQuery(all).getResultList();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Can't get all entities from table 'smiles'");
        }
        return smiles;
    }
}
