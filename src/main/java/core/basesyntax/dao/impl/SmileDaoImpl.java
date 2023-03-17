package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't add a smile to DB " + entity, e);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile = null;
        try (Session session = factory.openSession()) {
            smile = session.find(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find smile by id " + id, e);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smileList = null;
        try (Session session = factory.openSession()) {
            Query getAllCommentsQuery = session.createQuery("from Smile ", Smile.class);
            smileList = getAllCommentsQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comment from DB", e);
        }
        return smileList;
    }
}
