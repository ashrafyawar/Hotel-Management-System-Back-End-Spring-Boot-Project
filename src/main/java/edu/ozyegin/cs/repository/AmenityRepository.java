package edu.ozyegin.cs.repository;

import edu.ozyegin.cs.entity.Amenity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AmenityRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Amenity> findAll() {
        String sql = "SELECT * FROM Amenities";
        return jdbcTemplate.query(sql, new AmenityRowMapper());
    }

    public Amenity findById(int amenityId) {
        String sql = "SELECT * FROM Amenities WHERE AmenityID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {amenityId}, new AmenityRowMapper());
    }

    public void save(Amenity amenity) {
        String sql = "INSERT INTO Amenities(AmenityName) VALUES(?)";
        jdbcTemplate.update(sql, amenity.getAmenityName());
    }

    public void update(Amenity amenity) {
        String sql = "UPDATE Amenities SET AmenityName = ? WHERE AmenityID = ?";
        jdbcTemplate.update(sql, amenity.getAmenityName(), amenity.getAmenityId());
    }

    public void deleteById(int amenityId) {
        String sql = "DELETE FROM Amenities WHERE AmenityID = ?";
        jdbcTemplate.update(sql, amenityId);
    }

    class AmenityRowMapper implements RowMapper<Amenity> {
        @Override
        public Amenity mapRow(ResultSet rs, int rowNum) throws SQLException {
            Amenity amenity = new Amenity();
            amenity.setAmenityId(rs.getInt("AmenityID"));
            amenity.setAmenityName(rs.getString("AmenityName"));
            return amenity;
        }
    }
}
