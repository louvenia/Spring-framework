package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource ds;

    public UsersRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = "select * from users where id = " + id + ";";

        try {
            Connection conn = ds.getConnection();
            PreparedStatement prst = conn.prepareStatement(query);
            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
                return Optional.of(new User(rs.getLong(1), rs.getString(2)));
            }

            prst.close();
            conn.close();
        } catch (SQLException error) {
            error.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> listUser = new ArrayList<>();
        String query = "select * from users;";

        try {
            Connection conn = ds.getConnection();
            PreparedStatement prst = conn.prepareStatement(query);
            ResultSet rs = prst.executeQuery();

            while(rs.next()) {
                listUser.add(new User(rs.getLong(1), rs.getString(2)));
            }

            prst.close();
            conn.close();
        } catch(SQLException error) {
            error.printStackTrace();
        }

        return listUser;
    }

    @Override
    public void save(User entity) {
        String query = "insert into users(email) values(?);";

        try {
            Connection conn = ds.getConnection();
            PreparedStatement prst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            prst.setString(1, entity.getEmail());
            prst.executeUpdate();

            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }

            prst.close();
            conn.close();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        String query = "update users set email = ? where id = " + entity.getId() + ";";

        try {
            Connection conn = ds.getConnection();
            PreparedStatement prst = conn.prepareStatement(query, Statement.NO_GENERATED_KEYS);

            prst.setString(1, entity.getEmail());
            prst.executeUpdate();

            prst.close();
            conn.close();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "delete from users where id = " + id + ";";

        try {
            Connection conn = ds.getConnection();
            PreparedStatement prst = conn.prepareStatement(query);
            prst.executeUpdate();

            prst.close();
            conn.close();
        } catch(SQLException error) {
            error.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "select * from users where email = " + "'" + email + "'" + ";";

        try {
            Connection conn = ds.getConnection();
            PreparedStatement prst = conn.prepareStatement(query);
            ResultSet rs = prst.executeQuery();

            if (rs.next()) {
                return Optional.of(new User(rs.getLong(1), rs.getString(2)));
            }

            prst.close();
            conn.close();
        } catch (SQLException error) {
            error.printStackTrace();
        }

        return Optional.empty();
    }
}
