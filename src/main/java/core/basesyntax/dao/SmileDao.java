package core.basesyntax.dao;

import java.util.List;
import core.basesyntax.model.Smile;

public interface SmileDao {
    Smile create(Smile smile);

    Smile get(Long id);

    List<Smile> getAll();
}
