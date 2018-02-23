import domain.User;
import serviceimpl.BirthdayDiscountStrategy;
import serviceimpl.TicketNumberDiscountStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ContextConfiguration(locations = {"spring.xml"})
public class DiscountStrategiesTests {
    private BirthdayDiscountStrategy birthdayDiscountStrategy;
    private TicketNumberDiscountStrategy ticketNumberDiscountStrategy;

    @BeforeClass
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        birthdayDiscountStrategy = context.getBean(BirthdayDiscountStrategy.class);
        ticketNumberDiscountStrategy = context.getBean(TicketNumberDiscountStrategy.class);
    }

    @Test
    public void testTicketNumberDiscountStrategy() {
        boolean hasDiscount = ticketNumberDiscountStrategy.hasDiscount(null, null, null, 10);
        Assert.assertTrue(hasDiscount, "Ticket number discount strategy doen't work");

        hasDiscount = ticketNumberDiscountStrategy.hasDiscount(null, null, null, 0);
        Assert.assertFalse(hasDiscount, "Ticket number discount strategy doen't work");

        hasDiscount = ticketNumberDiscountStrategy.hasDiscount(null, null, null, 11);
        Assert.assertFalse(hasDiscount, "Ticket number discount strategy doen't work");

        hasDiscount = ticketNumberDiscountStrategy.hasDiscount(null, null, null, 9);
        Assert.assertFalse(hasDiscount, "Ticket number discount strategy doen't work");

        hasDiscount = ticketNumberDiscountStrategy.hasDiscount(null, null, null, 30);
        Assert.assertTrue(hasDiscount, "Ticket number discount strategy doen't work");
    }

    @Test
    public void testBirthdayStrategy() {
        LocalDateTime airDate = LocalDateTime.of(2019, 6, 15, 19, 30);

        User user = new User("First", "User", LocalDate.of(1990, 6, 15), "user@email.com");
        boolean hasDiscount = birthdayDiscountStrategy.hasDiscount(user, null, airDate, 1);
        Assert.assertTrue(hasDiscount, "Birthday disount stategy doesn't work");

        user = new User("Third", "User", LocalDate.of(1990, 6, 20), "user3@email.com");
        hasDiscount = birthdayDiscountStrategy.hasDiscount(user, null, airDate, 1);
        Assert.assertTrue(hasDiscount, "Birthday disount stategy doesn't work");

        user = new User("Third", "User", LocalDate.of(1990, 6, 10), "user3@email.com");
        hasDiscount = birthdayDiscountStrategy.hasDiscount(user, null, airDate, 1);
        Assert.assertTrue(hasDiscount, "Birthday disount stategy doesn't work");

        user = new User("Fourth", "User", LocalDate.of(1990, 6, 9), "user4@email.com");
        hasDiscount = birthdayDiscountStrategy.hasDiscount(user, null, airDate, 1);
        Assert.assertFalse(hasDiscount, "Birthday disount stategy doesn't work");

        user = new User("Fifth", "User", LocalDate.of(1990, 6, 21), "user5@email.com");
        hasDiscount = birthdayDiscountStrategy.hasDiscount(user, null, airDate, 1);
        Assert.assertFalse(hasDiscount, "Birthday disount stategy doesn't work");
    }

    @Test
    public void testBirthdayStrategyOnLeapYears() {
        User user = new User("Leap", "Year", LocalDate.of(1992, 2, 29), "LeapYear@email.com");
        LocalDateTime airDate = LocalDateTime.of(2019, 2, 23, 19, 30);
        boolean hasDiscount = birthdayDiscountStrategy.hasDiscount(user, null, airDate, 1);
        Assert.assertTrue(hasDiscount, "Birthday disount stategy doesn't work with leap years");

        airDate = LocalDateTime.of(2019, 2, 22, 19, 30);
        hasDiscount = birthdayDiscountStrategy.hasDiscount(user, null, airDate, 1);
        Assert.assertFalse(hasDiscount, "Birthday disount stategy doesn't work with leap years");

        airDate = LocalDateTime.of(2019, 3, 5, 19, 30);
        hasDiscount = birthdayDiscountStrategy.hasDiscount(user, null, airDate, 1);
        Assert.assertTrue(hasDiscount, "Birthday disount stategy doesn't work with leap years");

        airDate = LocalDateTime.of(2019, 3, 6, 19, 30);
        hasDiscount = birthdayDiscountStrategy.hasDiscount(user, null, airDate, 1);
        Assert.assertFalse(hasDiscount, "Birthday disount stategy doesn't work with leap years");
    }
}
