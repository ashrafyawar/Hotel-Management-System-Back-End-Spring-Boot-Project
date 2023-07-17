package edu.ozyegin.cs.repository;

import edu.ozyegin.cs.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoomRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Room> findAll() {
        String sql = "SELECT * FROM Rooms";
        return jdbcTemplate.query(sql, new RoomRowMapper());
    }

    public List<Room> findAllScheduledRooms(List<Integer> list) {
        String sql = "SELECT * FROM Rooms WHERE RoomId IN = (:list)";
        return jdbcTemplate.query(sql, new List[]{list},new RoomRowMapper());
    }
    public Room findById(int roomId) {
        String sql = "SELECT * FROM Rooms WHERE RoomID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomId}, new RoomRowMapper());
    }

    public Room findByName(String roomName) {
        String sql = "SELECT * FROM Rooms WHERE RoomName = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomName}, new RoomRowMapper());
    }

    public void save(Room room) {
        String sql = "INSERT INTO Rooms (RoomName, RoomType) VALUES (?,?,?)";
        jdbcTemplate.update(sql, room.getRoomName(), room.getRoomType());
    }

    public void update(String roomName, int roomId) {
        String sql = "UPDATE Rooms SET RoomName=? WHERE RoomID =?";
        jdbcTemplate.update(sql,roomName, roomId);
    }
    public void updateRoomType(int roomTypeId, int roomId) {
        String sql = "UPDATE Rooms SET RoomType=? WHERE RoomID =?";
        jdbcTemplate.update(sql,roomTypeId, roomId);
    }

    public void deleteById(int roomId) {
        String sql = "DELETE FROM Rooms WHERE RoomID = ?";
        jdbcTemplate.update(sql, roomId);
    }

    public List<Room> getRoomDetailsOfAtleastKAmenities(int roomId, int k) {
        String sql = "SELECT r.*\n" +
                "FROM Rooms r\n" +
                "INNER JOIN RoomAmenities ra ON r.RoomID = ra.RoomID\n" +
                "GROUP BY r.RoomID\n" +
                "HAVING COUNT(ra.AmenityID) >= ?";
        return jdbcTemplate.query(sql, new Object[]{k},new RoomRowMapper());
    }

    public Integer totalNumberOfFinishedHousekeepingTasks() {
        String sql = "SELECT COUNT(*)\n" +
                "FROM HousekeepingSchedules\n" +
                "WHERE Stat = 3;";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public Integer userIdWithMostCleanedRooms() {
        String sql = "SELECT AssignedTo\n" +
                "FROM HousekeepingSchedules\n" +
                "WHERE r.Stat = 3\n" +
                "GROUP BY AssignedTo\n" +
                "ORDER BY COUNT(*) DESC\n" +
                "LIMIT 1;";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public Integer getRoomCapacity(int roomType) {
        String sql = "SELECT RoomCapacity\n" +
                "FROM RoomTypes\n" +
                "WHERE TypeID = ?;";
        return jdbcTemplate.queryForObject(sql,new Object[]{roomType},Integer.class);
    }

    class RoomRowMapper implements RowMapper<Room> {

        @Override
        public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
            Room room = new Room();
            room.setRoomId(rs.getInt("RoomID"));
            room.setRoomName(rs.getString("RoomName"));
            room.setRoomType(rs.getInt("RoomType"));
            return room;
        }
    }
}

