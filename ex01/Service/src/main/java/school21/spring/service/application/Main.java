package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        User user1 = new User("jisooKim28@gmail.com");
        User user2 = new User("jennieKim27@list.ru");
        User user3 = new User("lisaManobal26@yandex.ru");
        User user4 = new User("rosePark26@mail.ru");

        System.out.println("----------------------usersRepositoryJdbc------------------------");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);

        usersRepository.save(user1);
        usersRepository.save(user2);

        System.out.println("-----Method findById------");
        Optional<User> user = usersRepository.findById(1L);
        if(user.isPresent()) {
            System.out.println(user.get());
        } else {
            System.err.println("User with this ID not found");
        }

        System.out.println("-----Method findAll------");
        System.out.println(usersRepository.findAll());

        System.out.println("-----Method update------");
        user1.setEmail("I_Love_Jisoo@gmail.com");
        usersRepository.update(user1);
        System.out.println(usersRepository.findAll());

        System.out.println("-----Method findByEmail------");
        Optional<User> userEmail = usersRepository.findByEmail("jennieKim27@list.ru");
        if(userEmail.isPresent()) {
            System.out.println(userEmail.get());
        } else {
            System.err.println("User with this Email not found");
        }

        System.out.println("-----Method delete------");
        usersRepository.delete(1L);
        System.out.println(usersRepository.findAll());

        System.out.println("----------------------usersRepositoryJdbcTemplate------------------------");
        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);

        usersRepository.save(user3);
        usersRepository.save(user4);

        System.out.println("-----Method findById------");
        user = usersRepository.findById(3L);
        if(user.isPresent()) {
            System.out.println(user.get());
        } else {
            System.err.println("User with this ID not found");
        }

        System.out.println("-----Method findAll------");
        System.out.println(usersRepository.findAll());

        System.out.println("-----Method update------");
        user3.setEmail("I'm_watching_anime_one_piece@gmail.com");
        usersRepository.update(user3);
        System.out.println(usersRepository.findAll());

        System.out.println("-----Method findByEmail------");
        userEmail = usersRepository.findByEmail("rosePark26@mail.ru");
        if(userEmail.isPresent()) {
            System.out.println(userEmail.get());
        } else {
            System.err.println("User with this Email not found");
        }

        System.out.println("-----Method delete------");
        usersRepository.delete(3L);
        System.out.println(usersRepository.findAll());
    }
}