package service;

import domain.Event;
import domain.User;

import java.time.LocalDateTime;

public interface DiscountService {

    byte getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets);

}
