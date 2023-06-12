package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import java.util.Optional;
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
        Session session = null;
        Transaction transaction = null;
        try {
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save smile " + entity + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile;
        try (Session session = super.factory.openSession()) {
            smile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile with id " + id + " from DB", e);
        }
        return Optional.ofNullable(smile).get();
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> resultList;
        try (Session session = super.factory.openSession()) {
            Query<Smile> allUsersQuery = session.createQuery("from Smile", Smile.class);
            resultList = allUsersQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles from DB", e);
        }
        return resultList;
    }
}
