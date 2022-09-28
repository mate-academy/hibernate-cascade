package core.basesyntax.service;

import core.basesyntax.model.Smile;
import java.util.List;

public interface SmileService {
    Smile create(Smile entity);
    
    Smile get(Long id);
    
    List<Smile> getAll();
}
