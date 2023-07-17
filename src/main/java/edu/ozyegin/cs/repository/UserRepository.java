package edu.ozyegin.cs.repository;

import edu.ozyegin.cs.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        String sql = "SELECT * FROM Users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public User findById(int userId) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {userId}, new UserRowMapper());
    }

    public void save(User user) {
        String sql = "INSERT INTO Users(Username, UserPassword, UserSalary, UserType) VALUES(?, ?, ?, ?)";
        String encryptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        jdbcTemplate.update(sql, user.getUsername(), encryptedPassword, user.getUserSalary(), user.getUserType());
    }

    public void update(User user) {
        String sql = "UPDATE Users SET Username = ?, UserPassword = ?, UserSalary = ?, UserType = ? WHERE UserID = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getUserSalary(), user.getUserType(), user.getUserId());
    }

    public void updateName(int userId,String newName) {
        String sql = "UPDATE Users SET Username = ? WHERE UserID = ?";
        jdbcTemplate.update(sql, newName, userId);
    }

    public void deleteById(int userId) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        jdbcTemplate.update(sql, userId);
    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("UserID"));
            user.setUsername(rs.getString("Username"));
            user.setPassword(rs.getString("UserPassword"));
            user.setUserSalary(rs.getInt("UserSalary"));
            user.setUserType(rs.getInt("UserType"));
            return user;
        }
    }
}

