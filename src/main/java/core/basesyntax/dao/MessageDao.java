package core.basesyntax.dao;

import core.basesyntax.model.Message;

public interface MessageDao extends GenericDao<Message> {
    void remove(Message message);

    Message get(Long id);
}
