package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SmileDaoImpl extends AbstractDao<Smile> implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile get(Long id) {
        Session session = super.factory.openSession();
        Smile smile = session.get(Smile.class, id);
        session.close();
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = super.factory.openSession()) {
            return session.createQuery("from Smile", Smile.class).getResultList();
        }
    }
}
