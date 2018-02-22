package service;

import domain.Event;
import domain.User;

import java.time.LocalDateTime;

public interface DiscountStrategy {

    boolean hasDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets);

    byte getDiscountSize();
}
