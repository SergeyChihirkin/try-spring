import domain.Auditorium;
import domain.Event;
import domain.EventRating;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.AuditoriumService;
import service.EventService;

import java.time.LocalDateTime;
import java.util.NavigableMap;

@ContextConfiguration(locations = {"spring.xml"})
public class EventTests {
    private static final String TEST_EVENT_NAME = "Test event";
    private static final String FIRST_AUDITORIUM_NAME = "firstAuditorium";
    private LocalDateTime airDate = LocalDateTime.of(2019, 6, 15, 19, 30);
    private EventService eventService;
    private AuditoriumService auditoriumService;
    private Event savedEvent;

    @BeforeClass
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        eventService = context.getBean(EventService.class);
        auditoriumService = context.getBean(AuditoriumService.class);

        removeTestEventsIfExist();
    }

    @AfterClass
    public void close() {
        removeTestEventsIfExist();
    }

    private void removeTestEventsIfExist() {
        Event event = eventService.getByName(TEST_EVENT_NAME);
        if (event != null)
            eventService.remove(event);
    }

    @Test
    public void saveEventTest() {
        int eventsNum = eventService.getAll().size();

        savedEvent = eventService.save(createFirstEvent());
        Assert.assertTrue(eventsNum + 1 == eventService.getAll().size(), "Number of events hasn't changed.");
    }

    @Test(dependsOnMethods = {"saveEventTest"})
    public void retrieveEventsTest() {
        Event eventByName = eventService.getByName(TEST_EVENT_NAME);
        Assert.assertTrue(eventByName.equals(savedEvent), "EventService has returned an unexpected user.");
    }

    @Test(dependsOnMethods = {"saveEventTest"})
    public void getByIdTest() {
        Event eventById = eventService.getById(savedEvent.getId());
        Assert.assertTrue(eventById.equals(savedEvent), "Hasn't managed to get event by id");
    }

    @Test(dependsOnMethods = {"saveEventTest"})
    public void assignAuditoriumTest() {
        Event event = eventService.getByName(TEST_EVENT_NAME);

        event.assignAuditorium(airDate, auditoriumService.getByName(FIRST_AUDITORIUM_NAME));
        NavigableMap<LocalDateTime, Auditorium> auditoriums = event.getAuditoriums();
        Auditorium assignedAuditorium = auditoriums.get(airDate);
        Assert.assertTrue(assignedAuditorium.equals(auditoriumService.getByName(FIRST_AUDITORIUM_NAME)),
                "Event service hasn't managed to assign auditorium.");

        boolean isAirs = event.airsOnDate(airDate.toLocalDate());
        Assert.assertTrue(isAirs, "Event doesn't air on specified date.");

        isAirs = event.airsOnDateTime(airDate);
        Assert.assertTrue(isAirs, "Event doesn't air on specified date.");

        isAirs = event.airsOnDates(airDate.toLocalDate(), airDate.toLocalDate());
        Assert.assertTrue(isAirs, "Event doesn't air on specified dates.");

        int auditoriumNumBeforeRemoving = event.getAuditoriums().size();
        boolean isAssignmentRemoved = event.removeAuditoriumAssignment(airDate);
        Assert.assertTrue(isAssignmentRemoved, "Hasn't managed to remove auditorium assignment.");
        Assert.assertTrue(event.getAuditoriums().size() + 1 == auditoriumNumBeforeRemoving,
                "Number of auditoriums changed wrongly after removing one of them.");
    }

    @Test(dependsOnMethods = {"saveEventTest", "retrieveEventsTest", "getByIdTest", "assignAuditoriumTest"})
    public void removeEventTest() {
        int eventsNum = eventService.getAll().size();

        eventService.remove(eventService.getByName(TEST_EVENT_NAME));
        Assert.assertTrue(eventsNum - 1 == eventService.getAll().size(), "Number of events hasn't changed.");
    }

    @SuppressWarnings("Duplicates")
    private Event createFirstEvent() {
        Event event = new Event();
        event.setName(TEST_EVENT_NAME);
        event.setRating(EventRating.MID);
        event.setBasePrice(10);

        return event;
    }
}
