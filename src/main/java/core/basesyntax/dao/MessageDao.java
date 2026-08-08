package core.basesyntax.dao;

import core.basesyntax.model.Message;
import java.util.List;

public interface MessageDao extends GenericDao<Message> {

    Message create(Message entity);

    Message get(Long id);

    List<Message> getAll();

    void remove(Message entity);
}
