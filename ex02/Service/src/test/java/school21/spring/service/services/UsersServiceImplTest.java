package school21.spring.service.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;

public class UsersServiceImplTest {
    private static AnnotationConfigApplicationContext context;
    private static UsersService usersService;
    private static UsersService usersServiceTemplate;

    @BeforeAll
    public static void init() {
        context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersService = context.getBean("usersService", UsersService.class);
        usersServiceTemplate = context.getBean("usersServiceTemplate", UsersService.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"jisooKim28@gmail.com", "jennieKin27@gmail.com", "lalisaManobal26@gmail.com", "rosePark26@gmail.com"})
    public void testSignUp(String email) {
        Assertions.assertNotNull(usersService.signUp(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"jisooKim28@gmail.com", "jennieKin27@gmail.com", "lalisaManobal26@gmail.com", "rosePark26@gmail.com"})
    public void testSignUpTemplate(String email) {
        Assertions.assertNotNull(usersServiceTemplate.signUp(email));
    }

    @AfterAll
    public static void close() {
        context.close();
    }
}
