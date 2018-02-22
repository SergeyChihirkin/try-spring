package serviceimpl;

import domain.Event;
import domain.User;
import org.springframework.beans.factory.annotation.Required;
import service.DiscountStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BirthdayDiscountStrategy implements DiscountStrategy {
    private int maxDaysDiff;
    private byte discountSize;

    @Override
    public boolean hasDiscount(User user, Event event, LocalDateTime airDateTime,
                               long numberOfTickets) {
        if (user == null || user.getBirthDate() == null)
            return false;

        LocalDate birthDate = user.getBirthDate();

        //to solve "leap year problem"
        LocalDate birthdayInTheYearOfAirDate = birthDate.withYear(airDateTime.getYear());

        int diff;
        if (birthdayInTheYearOfAirDate.compareTo(airDateTime.toLocalDate()) >= 0)
            diff = birthdayInTheYearOfAirDate.getDayOfYear() - airDateTime.getDayOfYear();
        else
            diff = airDateTime.getDayOfYear() - birthdayInTheYearOfAirDate.getDayOfYear();

        return diff <= maxDaysDiff;
    }

    @Override
    public byte getDiscountSize() {
        return discountSize;
    }

    @Required
    public void setDiscountSize(byte discountSize) {
        this.discountSize = discountSize;
    }

    @Required
    public void setMaxDaysDiff(int maxDaysDiff) {
        this.maxDaysDiff = maxDaysDiff;
    }
}
