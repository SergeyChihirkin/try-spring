package daosimpleimpl;

import dao.EventDao;
import domain.Event;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class EventDaoSimpleImpl implements EventDao {
    private Map<Long, Event> eventById =  new Hashtable<>();
    private long maxId = 0;

    @Override
    public Event save(Event object) {
        maxId++;
        object.setId(maxId);
        eventById.put(maxId, object);
        return object;
    }

    @Override
    public Event getByName(String name) {
        return eventById.values().stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void remove(Event object) {
        eventById.remove(object.getId());
    }

    @Override
    public Event getById(Long id) {
        return eventById.get(id);
    }

    @Override
    public Collection<Event> getAll() {
        return eventById.values();
    }
}
