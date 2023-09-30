package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.UUID;

@Component
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRep;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplate") UsersRepository usersRep) {
        this.usersRep = usersRep;
    }

    @Override
    public String signUp(String email) {
        if(email == null || email.isEmpty()) {
            System.err.println("Invalid email");
            return null;
        }
        String password = UUID.randomUUID().toString();
        User user = new User(email, password);
        usersRep.save(user);
        return "Generated password: " + password;
    }
}
