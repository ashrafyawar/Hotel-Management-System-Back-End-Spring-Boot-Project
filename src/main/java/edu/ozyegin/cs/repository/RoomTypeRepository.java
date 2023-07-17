package edu.ozyegin.cs.repository;

import edu.ozyegin.cs.entity.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoomTypeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RoomType> findAll() {
        String sql = "SELECT * FROM RoomTypes";
        return jdbcTemplate.query(sql, new RoomTypeRowMapper());
    }

    public RoomType findById(int typeId) {
        String sql = "SELECT * FROM RoomTypes WHERE TypeID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{typeId}, new RoomTypeRowMapper());
    }

    public RoomType findByName(String typeName) {
        String sql = "SELECT * FROM RoomTypes WHERE TypeName = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{typeName}, new RoomTypeRowMapper());
    }

    public void save(RoomType roomType) {
        String sql = "INSERT INTO RoomTypes (TypeName) VALUES (?)";
        jdbcTemplate.update(sql, roomType.getTypeName());
    }

    public void update(RoomType roomType) {
        String sql = "UPDATE RoomTypes SET TypeName = ? WHERE TypeID = ?";
        jdbcTemplate.update(sql, roomType.getTypeName(), roomType.getTypeId());
    }

    public void deleteById(int typeId) {
        String sql = "DELETE FROM RoomTypes WHERE TypeID = ?";
        jdbcTemplate.update(sql, typeId);
    }

    class RoomTypeRowMapper implements RowMapper<RoomType> {

        @Override
        public RoomType mapRow(ResultSet rs, int rowNum) throws SQLException {
            RoomType roomType = new RoomType();
            roomType.setTypeId(rs.getInt("TypeID"));
            roomType.setTypeName(rs.getString("TypeName"));
            return roomType;
        }
    }
}
