package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Smile create(Smile entity) {
        return null;
    }

    @Override
    public Smile get(Long id) {
        return null;
    }

    @Override
    public List<Smile> getAll() {
        return null;
    }
}
