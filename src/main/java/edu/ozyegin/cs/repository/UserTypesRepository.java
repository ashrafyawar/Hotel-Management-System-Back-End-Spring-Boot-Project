package edu.ozyegin.cs.repository;

//UserTypesRepository

import edu.ozyegin.cs.entity.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserTypesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserType> findAll() {
        String sql = "SELECT * FROM UserTypes";
        return jdbcTemplate.query(sql, new UserTypeRowMapper());
    }

    public UserType findById(int typeId) {
        String sql = "SELECT * FROM UserTypes WHERE TypeID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {typeId}, new UserTypeRowMapper());
    }

    public void save(UserType userType) {
        String sql = "INSERT INTO UserTypes(TypeName) VALUES(?)";
        jdbcTemplate.update(sql, userType.getTypeName());
    }

    public void update(UserType userType) {
        String sql = "UPDATE UserTypes SET TypeName = ? WHERE TypeID = ?";
        jdbcTemplate.update(sql, userType.getTypeName(), userType.getTypeId());
    }

    public void deleteById(int typeId) {
        String sql = "DELETE FROM UserTypes WHERE TypeID = ?";
        jdbcTemplate.update(sql, typeId);
    }

    class UserTypeRowMapper implements RowMapper<UserType> {
        @Override
        public UserType mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserType userType = new UserType();
            userType.setTypeId(rs.getInt("TypeID"));
            userType.setTypeName(rs.getString("TypeName"));
            return userType;
        }
    }
}
