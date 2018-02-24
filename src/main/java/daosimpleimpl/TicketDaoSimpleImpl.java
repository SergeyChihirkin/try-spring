package daosimpleimpl;

import dao.TicketDao;
import domain.Event;
import domain.Ticket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TicketDaoSimpleImpl implements TicketDao {
    private HashMap<Event, Set<Ticket>> ticketsByEvent = new HashMap<>();

    @Override
    public void addToStoredTickets(Ticket ticket) {
        Event event = ticket.getEvent();
        Set<Ticket> tickets = ticketsByEvent.computeIfAbsent(event, k -> new HashSet<>());

        tickets.add(ticket);
    }

    @Override
    public Set<Ticket> getTickets(Event event) {
        return ticketsByEvent.get(event);
    }
}
