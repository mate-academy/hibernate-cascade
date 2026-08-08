package core.basesyntax.dao;

import core.basesyntax.model.MessageDetails;
import java.util.List;

public interface MessageDetailsDao extends GenericDao<MessageDetails> {
    MessageDetails create(MessageDetails entity);

    MessageDetails get(Long id);

    List<MessageDetails> getAll();
}
