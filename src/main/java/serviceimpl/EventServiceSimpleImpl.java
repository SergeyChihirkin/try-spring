package serviceimpl;

import dao.EventDao;
import domain.Event;
import service.EventService;

import java.util.Collection;

public class EventServiceSimpleImpl implements EventService {
    private EventDao eventDao;

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event getByName(String name) {
        return eventDao.getByName(name);
    }

    @Override
    public Event save(Event object) {
        return eventDao.save(object);
    }

    @Override
    public void remove(Event object) {
        eventDao.remove(object);
    }

    @Override
    public Event getById(Long id) {
        return eventDao.getById(id);
    }

    @Override
    public Collection<Event> getAll() {
        return eventDao.getAll();
    }
}
