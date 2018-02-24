import domain.Event;
import domain.EventRating;
import domain.Ticket;
import domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.AuditoriumService;
import service.BookingService;
import service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BookingServiceTests {
    private static final LocalDateTime EVENT_DATE_TIME = LocalDateTime.of(2018, 6, 15, 0, 0);
    private static final LocalDate FIRST_USER_BIRTHDAY = LocalDate.of(1990, 6, 15);

    private BookingService bookingService;
    private UserService userService;
    private AuditoriumService auditoriumService;

    private User savedUser;

    private static final String TEST_EVENT_NAME = "Test event";
    private static final String FIRST_AUDITORIUM_NAME = "firstAuditorium";
    private static final String FIRST_USER_EMAIL = "first_test_user@gmail.com";
    private static final String FIRST_USER_FIRST_NAME = "First";
    private static final String FIRST_USER_LAST_NAME = "Test-User";


    @BeforeClass
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        bookingService = context.getBean(BookingService.class);
        userService = context.getBean(UserService.class);
        auditoriumService = context.getBean(AuditoriumService.class);

        removeTestUsersIfExist();

        savedUser = userService.save(createFirstUser());
    }

    @AfterClass
    public void close() {
        removeTestUsersIfExist();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        Event firstEvent = createFirstEvent();

        bookingService.getTicketsPrice(firstEvent, LocalDateTime.of(1990, 6, 15, 0, 0), savedUser, new HashSet<>());
    }

    @Test
    public void testBookingService() {
        Event firstEvent = createFirstEvent();

        Set<Long> seats = new HashSet<>(Arrays.asList(1L, 4L));
        double ticketsPrice = bookingService.getTicketsPrice(firstEvent, EVENT_DATE_TIME, savedUser, seats);
        Assert.assertTrue(ticketsPrice == 28.5, "Booking service has found tickets price wrongly");

        HashSet<Ticket> tickets = createTickets(savedUser, firstEvent, EVENT_DATE_TIME, seats);
        bookingService.bookTickets(tickets);
        Set<Ticket> purchasedTicketsForEvent = bookingService.getPurchasedTicketsForEvent(firstEvent, EVENT_DATE_TIME);
        Assert.assertTrue(purchasedTicketsForEvent.size() == 2,
                "Method getPurchasedTicketsForEvent has returned an unexpected number of tickets");

        seats = new HashSet<>(Arrays.asList(3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L));
        ticketsPrice = bookingService.getTicketsPrice(firstEvent, EVENT_DATE_TIME, savedUser, seats);
        Assert.assertTrue(ticketsPrice == 81, "Booking service has found tickets price wrongly");
        ticketsPrice = bookingService.getTicketsPrice(firstEvent, EVENT_DATE_TIME, null, seats);
        Assert.assertTrue(ticketsPrice == 85, "Booking service has found tickets price wrongly");
    }

    private HashSet<Ticket> createTickets(User savedUser, Event firstEvent, LocalDateTime eventDateTime, Set<Long> seats) {
        HashSet<Ticket> tickets = new HashSet<>();
        for (Long seat : seats) {
            Ticket ticket = new Ticket(savedUser, firstEvent, eventDateTime, seat);
            tickets.add(ticket);
        }
        return tickets;
    }

    @SuppressWarnings({"Duplicates", "UnnecessaryLocalVariable"})
    private User createFirstUser() {
        User user = new User(FIRST_USER_FIRST_NAME, FIRST_USER_LAST_NAME, FIRST_USER_BIRTHDAY, FIRST_USER_EMAIL);
        return user;
    }

    private void removeTestUsersIfExist() {
        User firstUser = userService.getUserByEmail(FIRST_USER_EMAIL);
        if (firstUser != null)
            userService.remove(firstUser);
    }

    @SuppressWarnings("Duplicates")
    private Event createFirstEvent() {
        Event event = new Event();
        event.setName(TEST_EVENT_NAME);
        event.setRating(EventRating.MID);
        event.setBasePrice(10);
        event.assignAuditorium(EVENT_DATE_TIME, auditoriumService.getByName(FIRST_AUDITORIUM_NAME));

        return event;
    }
}
