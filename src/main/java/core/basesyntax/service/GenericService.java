package core.basesyntax.service;

import java.util.List;

public interface GenericService<T> {
    T create(T entity);

    T get(Long id);

    List<T> getAll();

    void remove(T entity);
}
