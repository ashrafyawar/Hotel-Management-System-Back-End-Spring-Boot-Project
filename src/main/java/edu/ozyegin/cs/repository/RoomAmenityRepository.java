package edu.ozyegin.cs.repository;

import edu.ozyegin.cs.entity.Amenity;
import edu.ozyegin.cs.entity.Room;
import edu.ozyegin.cs.entity.RoomAmenity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoomAmenityRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void save(RoomAmenity roomAmenity) {
        String sql = "INSERT INTO RoomAmenities (RoomID, AmenityID) VALUES (?,?)";
        jdbcTemplate.update(sql, roomAmenity.getRoom(), roomAmenity.getAmenity());
    }

    public void deleteByRoomId(int roomId) {
        String sql = "DELETE FROM RoomAmenities WHERE RoomID = ?";
        jdbcTemplate.update(sql, roomId);
    }

    public void deleteByRoomIdAndAmenityId(int roomId,int amenityId) {
        String sql = "DELETE FROM RoomAmenities WHERE RoomID = ? AND AmenityID = ?";
        jdbcTemplate.update(sql, roomId, amenityId);
    }

    public List<Amenity> findAmenitiesByRoomId(int roomId) {
        String sql = "SELECT AmenityID FROM RoomAmenities WHERE RoomID = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{roomId}, Integer.class)
                .stream()
                .map(amenityId -> amenityRepository.findById(amenityId))
                .collect(Collectors.toList());
    }

    public List<Room> findRoomsByAmenityId(int amenityId) {
        String sql = "SELECT RoomID FROM RoomAmenities WHERE AmenityID = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{amenityId}, Integer.class)
                .stream()
                .map(roomId -> roomRepository.findById(roomId))
                .collect(Collectors.toList());
    }

    public List<RoomAmenity> findAll() {
        String sql = "SELECT * FROM RoomAmenities";
        return jdbcTemplate.query(sql, new RoomAmenityRepository.RoomAmenityRowMapper());
    }

    public Double averageBookingPriceForRoomsWithAtLeastKAmenities(int k) {
        String sql = "SELECT AVG(b.Price)\n" +
                "FROM Bookings b\n" +
                "JOIN RoomAmenities ra ON ra.RoomID = b.RoomID\n" +
                "GROUP BY ra.RoomID\n" +
                "HAVING COUNT(DISTINCT ra.AmenityID) >= ?\n" +
                "LIMIT 1;";
        return jdbcTemplate.queryForObject(sql,new Object[]{k},Double.class);
    }

    public List<Integer> idsOfMostCommonAmenities() {
        String sql = "SELECT AmenityID\n" +
                "FROM RoomAmenities\n" +
                "GROUP BY AmenityID\n" +
                "ORDER BY COUNT(AmenityID) DESC;";
        return jdbcTemplate.queryForList(sql, Integer.class);
    }

    public Double priceDifferenceBetweenRoomsWithMostAndSecondMostCommonAmenities() {
        String sql = "WITH common_amenities AS (\n" +
                "	SELECT AmenityID, COUNT(AmenityID) as 'Count'\n" +
                "	FROM RoomAmenities\n" +
                "	GROUP BY AmenityID\n" +
                "	ORDER BY Count DESC\n" +
                "	LIMIT 2\n" +
                ") \n"+
                "SELECT SUM(CASE WHEN ra1.AmenityID = common_amenities.AmenityID AND ra2.AmenityID != common_amenities.AmenityID THEN b1.Price ELSE 0 END) - \n" +
                "       SUM(CASE WHEN ra1.AmenityID != common_amenities.AmenityID AND ra2.AmenityID = common_amenities.AmenityID THEN b2.Price ELSE 0 END) AS total_price_difference\n" +
                "FROM Rooms r\n" +
                "JOIN RoomAmenities ra1 ON ra1.RoomID = r.RoomID\n" +
                "JOIN RoomAmenities ra2 ON ra2.RoomID = r.RoomID\n" +
                "JOIN common_amenities on common_amenities.AmenityID = ra1.AmenityID or common_amenities.AmenityID = ra2.AmenityID\n" +
                "JOIN Bookings b1 ON b1.RoomID = r.RoomID\n" +
                "JOIN Bookings b2 ON b2.RoomID = r.RoomID";
        return jdbcTemplate.queryForObject(sql, new Object[]{}, Double.class);
    }


    class RoomAmenityRowMapper implements RowMapper<RoomAmenity> {

        @Override
        public RoomAmenity mapRow(ResultSet rs, int rowNum) throws SQLException {
            RoomAmenity roomAmenity = new RoomAmenity();
//            roomAmenity.setId(rs.getInt("Id"));
            roomAmenity.setRoom(rs.getInt("RoomId"));
            roomAmenity.setAmenity(rs.getInt("AmenityID"));
            return roomAmenity;
        }
    }
}

