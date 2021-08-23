package core.basesyntax.dao;

import core.basesyntax.model.Message;
import java.util.List;

public interface MessageDao extends GenericDao<Message> {
    Message create(Message message);

    Message get(Long id);

    List<Message> getAll();

    void remove(Message message);
}
