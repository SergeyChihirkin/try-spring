package service;

import domain.DomainObject;

import java.util.Collection;

public interface AbstractDomainObjectService<T extends DomainObject> {

    T save(T object);

    void remove(T object);

    T getById(Long id);

    Collection<T> getAll();
}
