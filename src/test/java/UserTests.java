import domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.UserService;

@ContextConfiguration(locations = {"spring.xml"})
public class UserTests {
    private static final String FIRST_USER_EMAIL = "first_test_user@gmail.com";
    private static final String FIRST_USER_FIRST_NAME = "First";
    private static final String FIRST_USER_LAST_NAME = "Test-User";
    private UserService userService;
    private User savedUser;

    @BeforeClass
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        userService = context.getBean(UserService.class);

        removeTestUsersIfExist();
    }

    @AfterClass
    public void close() {
        removeTestUsersIfExist();
    }

    private void removeTestUsersIfExist() {
        User firstUser = userService.getUserByEmail(FIRST_USER_EMAIL);
        if (firstUser != null)
            userService.remove(firstUser);
    }

    @SuppressWarnings("Duplicates")
    private User createFirstUser() {
        User user = new User();
        user.setFirstName(FIRST_USER_FIRST_NAME);
        user.setLastName(FIRST_USER_LAST_NAME);
        user.setEmail(FIRST_USER_EMAIL);
        return user;
    }

    @Test
    public void saveUserTest() {
        int usersNum = userService.getAll().size();

        savedUser = userService.save(createFirstUser());
        Assert.assertTrue(usersNum + 1 == userService.getAll().size(), "Number of users hasn't changed.");
    }

    @Test(dependsOnMethods = {"saveUserTest"})
    public void retrieveUsersTest() {
        User userByEmail = userService.getUserByEmail(FIRST_USER_EMAIL);
        Assert.assertTrue(userByEmail.equals(savedUser), "UserService has returned an unexpected user.");
    }

    @Test(dependsOnMethods = {"saveUserTest", "retrieveUsersTest"})
    public void removeUserTest() {
        int usersNum = userService.getAll().size();

        userService.remove(userService.getUserByEmail(FIRST_USER_EMAIL));
        Assert.assertTrue(usersNum - 1 == userService.getAll().size(), "Number of users hasn't changed.");
    }

    @Test(dependsOnMethods = {"saveUserTest"})
    public void getByIdTest() {
        User userById = userService.getById(savedUser.getId());
        Assert.assertTrue(userById.equals(savedUser), "Hasn't managed to get user by id");
    }
}
