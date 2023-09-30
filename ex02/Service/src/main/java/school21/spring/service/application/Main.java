package school21.spring.service.application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        System.out.println("----------------------usersRepositoryJdbc------------------------");
        UsersRepository usersRepository1 = context.getBean("usersRepositoryJdbc", UsersRepository.class);

        User user1 = new User("jisooKim28@gmail.com", "qwerty");
        User user2 = new User("jennieKim27@gmail.com", "qwerty");
        User user3 = new User("lalisaManobal26@gmail.com", "qwerty");
        User user4 = new User("rosePark26@gmail.com", "qwerty");

        usersRepository1.save(user1);
        usersRepository1.save(user2);

        System.out.println("-----Method findById------");
        Optional<User> user = usersRepository1.findById(1L);
        if(user.isPresent()) {
            System.out.println(user.get());
        } else {
            System.err.println("User with this ID not found");
        }

        System.out.println("-----Method findAll------");
        System.out.println(usersRepository1.findAll());

        System.out.println("-----Method update------");
        user1.setEmail("I_Love_Jisoo@gmail.com");
        usersRepository1.update(user1);
        System.out.println(usersRepository1.findAll());

        System.out.println("-----Method findByEmail------");
        Optional<User> userEmail = usersRepository1.findByEmail("jennieKim27@gmail.com");
        if(userEmail.isPresent()) {
            System.out.println(userEmail.get());
        } else {
            System.err.println("User with this Email not found");
        }

        System.out.println("-----Method delete------");
        usersRepository1.delete(1L);
        System.out.println(usersRepository1.findAll());

        System.out.println("----------------------usersRepositoryJdbcTemplate------------------------");
        UsersRepository usersRepository2 = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);

        usersRepository2.save(user3);
        usersRepository2.save(user4);

        System.out.println("-----Method findById------");
        user = usersRepository2.findById(3L);
        if(user.isPresent()) {
            System.out.println(user.get());
        } else {
            System.err.println("User with this ID not found");
        }

        System.out.println("-----Method findAll------");
        System.out.println(usersRepository2.findAll());

        System.out.println("-----Method update------");
        user3.setEmail("I'm_watching_anime_one_piece@gmail.com");
        usersRepository2.update(user3);
        System.out.println(usersRepository2.findAll());

        System.out.println("-----Method findByEmail------");
        userEmail = usersRepository2.findByEmail("rosePark26@gmail.com");
        if(userEmail.isPresent()) {
            System.out.println(userEmail.get());
        } else {
            System.err.println("User with this Email not found");
        }

        System.out.println("-----Method delete------");
        usersRepository2.delete(3L);
        System.out.println(usersRepository2.findAll());

        System.out.println("-----Method singUp------");
        UsersService userService = context.getBean(UsersService.class);
        System.out.println(userService.signUp("KimJisoo@gmail.com"));
        System.out.println(userService.signUp("KimJennie@list.ru"));
        System.out.println(userService.signUp("ManobalLalisa@yandex.ru"));
        System.out.println(userService.signUp("ParkRose@mail.ru"));

        System.out.println("-----Method FindAll Hikari------");
        for(User userR : usersRepository1.findAll()) {
            System.out.println(userR);
        }

        System.out.println("-----Method FindAll JdbcTemplate------");
        for(User userR : usersRepository2.findAll()) {
            System.out.println(userR);
        }

        context.close();
    }
}