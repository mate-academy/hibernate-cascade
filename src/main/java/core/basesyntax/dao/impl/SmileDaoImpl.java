package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.SessionFactory;

public class SmileDaoImpl extends AbstractDao<Smile> implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    @Override
    public Smile create(Smile entity) {
        return super.create(entity);
    }
    
    @Override
    public Smile get(Long id) {
        return super.get(Smile.class, id);
    }
    
    @Override
    public List<Smile> getAll() {
        return super.getAll(Smile.class);
    }
    
    @Override
    public void remove(Smile entity) {
        super.remove(entity);
    }
}
