package core.basesyntax.service.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import core.basesyntax.service.SmileService;
import java.util.List;

public class SmileServiceImpl implements SmileService {
    private SmileDao dao;
    
    public SmileServiceImpl(SmileDao dao) {
        this.dao = dao;
    }
    
    @Override
    public Smile create(Smile entity) {
        return dao.create(entity);
    }
    
    @Override
    public Smile get(Long id) {
        return dao.get(id);
    }
    
    @Override
    public List<Smile> getAll() {
        return dao.getAll();
    }
}
