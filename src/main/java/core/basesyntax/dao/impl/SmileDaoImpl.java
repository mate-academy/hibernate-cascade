package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
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
                throw new RuntimeException("Can`t save smile " + entity + " to DB");
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
            smile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can`t find smile with id = " + id + " in Db");
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> list = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query<Smile> query = session.createQuery("FROM Smile", Smile.class);
            list = query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can`t find any smiles in Db");
        }
        return list;
    }
}
