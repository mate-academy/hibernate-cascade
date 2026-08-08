package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

// 1. Upewnij się, że implementujesz SmileDao, który rozszerza GenericDao<Smile>
public class SmileDaoImpl extends AbstractDao implements SmileDao {

    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile smile) { // Typ MUSI być Smile, nazwa MUSI być create
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(smile);
            transaction.commit();
            return smile;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert smile: " + smile, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) { // Typ zwracany MUSI być Smile
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() { // Typ listy MUSI być List<Smile>
        try (Session session = factory.openSession()) {
            return session.createQuery("from Smile", Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles", e);
        }
    }

    @Override
    public void remove(Smile smile) { // Parametr MUSI być Smile
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(smile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove smile: " + smile, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
