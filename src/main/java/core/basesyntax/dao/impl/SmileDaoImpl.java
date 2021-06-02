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
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t save Smile " + entity);
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            Smile smile = null;
            smile = session.get(Smile.class, id);
            return smile;
        } catch (Exception e) {
            throw new RuntimeException("Can`t get Smile by id " + id);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Smile", Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can`t get all Smiles from DB");
        }
    }
}
