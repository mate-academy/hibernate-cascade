package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = super.factory.openSession();
            transaction = currentSession.beginTransaction();
            currentSession.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create smile: " + entity,e);
        } finally {
            currentSession.close();
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session currentSession = super.factory.openSession();) {
            Smile smile = currentSession.get(Smile.class, id);
            return smile;
        } catch (Exception e) {
            throw new RuntimeException("Can't get by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session currentSession = super.factory.openSession();) {
            Query<Smile> commentsQuery = currentSession.createQuery("from Smile ", Smile.class);
            return commentsQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get smiles", e);
        }
    }
}
