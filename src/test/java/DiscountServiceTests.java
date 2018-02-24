import domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.DiscountService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ContextConfiguration(locations = {"spring.xml"})
public class DiscountServiceTests {
    private DiscountService discountService;

    @BeforeClass
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        discountService = context.getBean(DiscountService.class);
    }

    @Test
    public void discountServiceTests() {
        LocalDateTime airDate = LocalDateTime.of(2019, 6, 15, 19, 30);

        User user = new User("First", "User", LocalDate.of(1990, 6, 15), "user@email.com");
        byte discount = discountService.getDiscount(user, null, airDate, 10);
        Assert.assertTrue(discount == 50, "Discount service doen't work correctly.");

        discount = discountService.getDiscount(user, null, airDate, 9);
        Assert.assertTrue(discount == 5, "Discount service doen't work correctly.");

        User user2 = new User("First", "User", LocalDate.of(1990, 5, 15), "user@email.com");
        discount = discountService.getDiscount(user2, null, airDate, 9);
        Assert.assertTrue(discount == 0, "Discount service doen't work correctly.");
    }
}
