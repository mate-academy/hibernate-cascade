package core.basesyntax.dao;

import java.util.List;

public interface GenericDao<T> {
    void create(T entity);

    T get(Long id);

    List<T> getAll();

    void remove(T entity);
}
