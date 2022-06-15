package core.basesyntax.dao;

import core.basesyntax.model.Smile;
import java.util.List;

public interface SmileDao extends GenericDao<Smile> {
    Smile create(Smile smile);

    Smile get(Long id);

    List<Smile> getAll();
}
