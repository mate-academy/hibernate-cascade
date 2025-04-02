package core.basesyntax.service.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.lib.Inject;
import core.basesyntax.model.Smile;
import core.basesyntax.service.SmileService;
import java.util.List;

public class SmileServiceImpl implements SmileService {
    @Inject
    private SmileDao smileDao;

    @Override
    public Smile create(Smile entity) {
        return smileDao.create(entity);
    }

    @Override
    public Smile get(Long id) {
        Smile smile = smileDao.get(id);
        if (smile == null) {
            throw new DataProcessingException("Smile not found for id: " + id);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smiles = smileDao.getAll();
        if (smiles == null) {
            throw new DataProcessingException("Smile not found for id's");
        }
        return smiles;
    }
}
