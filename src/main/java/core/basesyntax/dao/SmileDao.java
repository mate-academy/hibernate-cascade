package core.basesyntax.dao;

import core.basesyntax.model.Smile;
import java.util.List;

public interface SmileDao {
    Smile get(Long id);

    List<Smile> getAll();
}
