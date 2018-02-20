import domain.Auditorium;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.AuditoriumService;

import java.util.Set;

@ContextConfiguration(locations = {"spring.xml"})
public class AuditoriumTests {
    private AuditoriumService auditoriumService;

    @BeforeClass
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        auditoriumService = context.getBean(AuditoriumService.class);
    }

    @Test
    public void getAllAuditoriumsTest() {
        Set<Auditorium> auditoriums = auditoriumService.getAll();
        Assert.assertTrue(auditoriums.size() != 0, "Number of auditoriums must be more than 0.");
    }

    @Test
    public void getAuditoriumByName() {
        Auditorium firstAuditorium = auditoriumService.getByName("firstAuditorium");

        Assert.assertTrue(firstAuditorium != null, "Hasn't managed to get auditorium by name.");
    }

    @Test
    public void countVipSeatsTest() {
        Auditorium firstAuditorium = auditoriumService.getByName("firstAuditorium");

        Assert.assertTrue(firstAuditorium.countVipSeats(firstAuditorium.getAllSeats()) == 3,
                "Hasn't managed to count vip seats");
    }
}
