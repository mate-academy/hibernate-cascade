package core.basesyntax.service;

import core.basesyntax.model.MessageDetails;

public interface MessageDetailsService {
    MessageDetails create(MessageDetails entity);

    MessageDetails get(Long id);
}
