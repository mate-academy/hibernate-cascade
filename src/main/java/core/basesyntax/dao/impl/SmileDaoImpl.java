package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.SessionFactory;

public class SmileDaoImpl extends AbstractDao<Smile> implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Smile.class);
    }

    @Override
    public Smile create(Smile smile) {
        save(smile);
        return smile;
    }

    @Override
    public Smile get(Long id) {
        return findById(id);
    }

    @Override
    public List<Smile> getAll() {
        return super.getAll(Smile.class);
    }
}
