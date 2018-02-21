package service;

import domain.Event;

public interface EventService extends AbstractDomainObjectService<Event> {

    Event getByName(String name);
}
