package serviceimpl;

import domain.Event;
import domain.User;
import service.DiscountStrategy;

import java.time.LocalDateTime;

public class TicketNumberDiscountStrategy implements DiscountStrategy {
    private byte discountSize;

    @Override
    public boolean hasDiscount(User user, Event event, LocalDateTime airDateTime,
                               long numberOfTickets) {
        return (numberOfTickets > 0) && (numberOfTickets % 10 == 0);
    }

    @Override
    public byte getDiscountSize() {
        return discountSize;
    }

    public void setDiscountSize(byte discountSize) {
        this.discountSize = discountSize;
    }
}
