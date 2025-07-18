package core.basesyntax.dao;

import core.basesyntax.model.Message;
import java.util.List;

public interface MessageDao extends GenericDao<Message> {
    public Message create(Message entity);

    public Message get(Long id);

    public List<Message> getAll();

    public void remove(Message entity);
}
