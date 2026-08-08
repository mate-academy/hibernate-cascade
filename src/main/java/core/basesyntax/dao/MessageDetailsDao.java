package core.basesyntax.dao;

import core.basesyntax.model.MessageDetails;
import java.util.List;

public interface MessageDetailsDao {
    MessageDetails create(MessageDetails entity);

    MessageDetails get(Long id);

    void remove(MessageDetails entity);

    List<MessageDetails> getAll();
}
