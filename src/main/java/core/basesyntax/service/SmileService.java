package core.basesyntax.service;

import core.basesyntax.model.Smile;
import java.util.List;

public interface SmileService {
    Smile create(Smile smile);

    Smile get(Long id);

    List<Smile> getAll();
}
