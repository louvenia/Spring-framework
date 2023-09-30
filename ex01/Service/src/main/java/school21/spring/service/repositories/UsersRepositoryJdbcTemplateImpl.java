package school21.spring.service.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final NamedParameterJdbcTemplate ds;

    public UsersRepositoryJdbcTemplateImpl(DataSource ds) {
        this.ds = new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(ds.query("select * from users where id = :id;",
                                                new MapSqlParameterSource("id", id),
                                                new BeanPropertyRowMapper<>(User.class))
                                                .stream().findAny().orElse(null));
    }

    @Override
    public List<User> findAll() {
        return ds.query("select * from users;", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User entity) {
        ds.update("insert into users(email) values(:email);", new MapSqlParameterSource("email", entity.getEmail()));
    }

    @Override
    public void update(User entity) {
        ds.update("update users set email = :email where id = :id;",
                    new MapSqlParameterSource("email", entity.getEmail())
                                            .addValue("id", entity.getId()));
    }

    @Override
    public void delete(Long id) {
        ds.update("delete from users where id = :id;", new MapSqlParameterSource("id", id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(ds.query("select * from users where email = :email;",
                                                new MapSqlParameterSource("email", email),
                                                new BeanPropertyRowMapper<>(User.class))
                                                .stream().findAny().orElse(null));
    }
}
