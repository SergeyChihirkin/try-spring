package serviceimpl;

import dao.TicketDao;
import domain.Event;
import domain.EventRating;
import domain.Ticket;
import domain.User;
import service.BookingService;
import service.DiscountService;
import service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingServiceSimpleImpl implements BookingService {
    private TicketDao ticketDao;

    private UserService userService;
    private DiscountService discountService;

    private int vipMultiplier;
    private double highRatingMultipier;

    public BookingServiceSimpleImpl(UserService userService, DiscountService discountService) {
        this.userService = userService;
        this.discountService = discountService;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats) {
        if (!event.airsOnDateTime(dateTime))
            throw new IllegalArgumentException(String.format("Event doesn't have this air date - %s", dateTime));

        double ticketsPrice = 0;

        int countOfTicket = user == null ? seats.size() : user.getTickets().size();

        double basePrice = event.getBasePrice();
        if (event.getRating().equals(EventRating.HIGH))
            basePrice *= highRatingMultipier;

        for (Long seat : seats) {
            countOfTicket++;
            double price = basePrice;

            if (event.getAuditoriums().get(dateTime).getVipSeats().contains(seat))
                price *= vipMultiplier;

            byte discount = discountService.getDiscount(user, event, dateTime, countOfTicket);
            if (discount != 0)
                price -= price * discount / 100;

            ticketsPrice += price;
        }

        return ticketsPrice;
    }

    @Override
    public void bookTickets(Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            User user = ticket.getUser();

            if (user == null || ticket.getEvent() == null || ticket.getDateTime() == null)
                return;

            if (user.getId() == null)
                userService.save(user);

            if (userService.getById(user.getId()) != null) {
                Ticket storedTicket =
                        new Ticket(ticket.getUser(), ticket.getEvent(), ticket.getDateTime(), ticket.getSeat());

                user.getTickets().add(storedTicket);
                ticketDao.addToStoredTickets(storedTicket);
            }
        }
    }



    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
        Set<Ticket> tickets = ticketDao.getTickets(event);
        if (tickets != null)
            return tickets.stream().filter(t -> t.getDateTime() != null && t.getDateTime().equals(dateTime)).
                    collect(Collectors.toSet());

        return new HashSet<>();
    }

    public void setVipMultiplier(int vipMultiplier) {
        this.vipMultiplier = vipMultiplier;
    }

    public void setHighRatingMultipier(double highRatingMultipier) {
        this.highRatingMultipier = highRatingMultipier;
    }
}
