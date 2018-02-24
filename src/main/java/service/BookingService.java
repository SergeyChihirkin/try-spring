package service;

import domain.Event;
import domain.Ticket;
import domain.User;

import java.time.LocalDateTime;
import java.util.Set;

public interface BookingService {

    double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats);

    void bookTickets(Set<Ticket> tickets);

    Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime);

}
