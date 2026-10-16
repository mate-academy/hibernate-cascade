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
    public Smile create(Smile smile) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(smile);
            transaction.commit();
            return smile;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Can't create new smile " + smile, e);
        } finally {
            session.close();
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find smile with id " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        String sql = "SELECT * FROM smile";
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(sql, Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles ", e);
        }
    }
}
