package dao;

import domain.Event;

import java.util.Collection;

public interface EventDao {
    Event save(Event object);

    Event getByName(String name);

    void remove(Event object);

    Event getById(Long id);

    Collection<Event> getAll();
}
