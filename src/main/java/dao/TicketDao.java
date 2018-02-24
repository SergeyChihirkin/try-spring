package dao;

import domain.Event;
import domain.Ticket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public interface TicketDao {
    void addToStoredTickets(Ticket ticket);

    Set<Ticket> getTickets(Event event);
}
