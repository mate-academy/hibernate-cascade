package core.basesyntax.service;

import core.basesyntax.model.Message;
import java.util.List;

public interface MessageService {
    Message create(Message entity);
    
    Message get(Long id);
    
    List<Message> getAll();
    
    void remove(Message entity);
}
