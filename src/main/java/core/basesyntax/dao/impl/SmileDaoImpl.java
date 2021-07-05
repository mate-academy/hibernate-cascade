package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Log4j
public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile smile) {
        log.info("Calling a create() method of SmileDaoImpl class");
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(smile);
            log.info("Attempt to store smile " + smile + " in db.");
            transaction.commit();
            return smile;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create smile entity. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        log.info("Calling a get() method of SmileDaoImpl class");
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        }
    }

    @Override
    public List<Smile> getAll() {
        log.info("Calling a getAll() method of SmileDaoImpl class");
        try (Session session = factory.openSession()) {
            CriteriaQuery<Smile> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Smile.class);
            criteriaQuery.from(Smile.class);
            List<Smile> list = session.createQuery(criteriaQuery).getResultList();
            log.info("Attempt to retrieve collections of smiles from db.");
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all smiles. ", e);
        }
    }
}
