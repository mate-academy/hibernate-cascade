package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        return (Smile) super.createEntity(entity);
    }

    @Override
    public Smile get(Long id) {
        Optional<Smile> smile = super.get(id, Smile.class);
        return smile.orElse(null);
    }

    @Override
    public List<Smile> getAll() {
        return super.getAll(Smile.class);
    }
}
