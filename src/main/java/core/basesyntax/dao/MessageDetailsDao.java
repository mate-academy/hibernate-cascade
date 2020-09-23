package core.basesyntax.dao;

import core.basesyntax.model.MessageDetails;

public interface MessageDetailsDao {
    MessageDetails create(MessageDetails entity);

    MessageDetails get(Long id);
}
