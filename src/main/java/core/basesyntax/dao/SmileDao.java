package core.basesyntax.dao;

import core.basesyntax.model.Smile;
import java.util.List;

public interface SmileDao {
    Smile create(Smile smile);

    Smile get(Long id);

    List<Smile> getAll();
}
