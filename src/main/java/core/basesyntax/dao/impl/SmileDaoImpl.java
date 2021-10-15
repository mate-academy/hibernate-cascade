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
    public Smile create(Smile smile) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(smile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(smile + " wasn't added to DB!", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Message with id: " + id + " wasn't found!", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            String hql = "FROM Smile ";
            Query query = session.createQuery(hql);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Smile weren't found!", e);
        }
    }
}
