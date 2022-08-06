package core.basesyntax.dao;

import core.basesyntax.model.Message;

import java.util.List;

public interface MessageDao extends GenericDao<Message> {
    @Override
    Message create(Message entity);

    @Override
    Message get(Long id);

    @Override
    List<Message> getAll();

    @Override
    void remove(Message entity);
}
