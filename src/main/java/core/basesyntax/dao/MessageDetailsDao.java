package core.basesyntax.dao;

import core.basesyntax.model.MessageDetails;

public interface MessageDetailsDao {
    MessageDetails create(MessageDetails messageDetails);

    MessageDetails get(Long id);
}
