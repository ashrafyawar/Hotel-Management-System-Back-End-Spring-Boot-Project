package edu.ozyegin.cs.repository;

import edu.ozyegin.cs.entity.HousekeepingSchedule;
import edu.ozyegin.cs.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HousekeepingSchedulesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HKScheduleStatusTypesRepository hkScheduleStatusTypesRepository;

    public List<HousekeepingSchedule> findAll() {
        String sql = "SELECT * FROM HousekeepingSchedules";
        return jdbcTemplate.query(sql, new HousekeepingScheduleRowMapper());
    }
    public List<HousekeepingSchedule> findAllStat1() {
        String sql = "SELECT * FROM HousekeepingSchedules WHERE Stat = 1";
        return jdbcTemplate.query(sql, new HousekeepingScheduleRowMapper());
    }

    public HousekeepingSchedule findById(int scheduleId) {
        String sql = "SELECT * FROM HousekeepingSchedules WHERE ScheduleID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {scheduleId}, new HousekeepingScheduleRowMapper());
    }

    public void save(HousekeepingSchedule schedule) {
        String sql = "INSERT INTO HousekeepingSchedules(RoomID, AssignedTo, TaskDate, Stat) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getRoom(), schedule.getAssignedTo(), schedule.getTaskDate(), schedule.getStat());
    }

    public void update(HousekeepingSchedule schedule) {
        String sql = "UPDATE HousekeepingSchedules SET RoomID = ?, AssignedTo = ?, TaskDate = ?, Stat = ? WHERE ScheduleID = ?";
        jdbcTemplate.update(sql, schedule.getRoom(), schedule.getAssignedTo(), schedule.getTaskDate(), schedule.getStat(), schedule.getScheduleId());
    }

    public void deleteById(int scheduleId) {
        String sql = "DELETE FROM HousekeepingSchedules WHERE ScheduleID = ?";
        jdbcTemplate.update(sql, scheduleId);
    }

    public Integer housekeeperWithMostPendingTasks() {
        String sql = "SELECT AssignedTo\n" +
                "FROM HouseKeepingSchedules\n" +
                "WHERE Stat IN (1, 2)\n" +
                "GROUP BY AssignedTo\n" +
                "ORDER BY COUNT(*) DESC\n" +
                "LIMIT 1;";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public List<Integer> roomsWithHighestNumberOfDifferentHousekeepers() {
        String sql = "SELECT RoomID\n" +
                "FROM HousekeepingSchedules\n" +
                "GROUP BY RoomID\n" +
                "HAVING COUNT(DISTINCT AssignedTo) = (\n" +
                "  SELECT COUNT(DISTINCT AssignedTo)\n" +
                "  FROM HousekeepingSchedules\n" +
                "  GROUP BY RoomID\n" +
                "  ORDER BY COUNT(DISTINCT AssignedTo) DESC\n" +
                "  LIMIT 1\n" +
                ");";
        return jdbcTemplate.queryForObject(sql,List.class);
    }

    public void setHouseKeepingScheduleToInProgress(int scheduleId) {
        String sql = "UPDATE HousekeepingSchedules SET Stat = 2 WHERE ScheduleID = ?";
        jdbcTemplate.update(sql, scheduleId);
    }

    public void setHouseKeepingScheduleToDone(int scheduleId) {
        String sql = "UPDATE HousekeepingSchedules SET Stat = 3 WHERE ScheduleID = ?";
        jdbcTemplate.update(sql, scheduleId);
    }

    class HousekeepingScheduleRowMapper implements RowMapper<HousekeepingSchedule> {

        @Override
        public HousekeepingSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
            HousekeepingSchedule schedule = new HousekeepingSchedule();
            schedule.setScheduleId(rs.getInt("ScheduleID"));
            schedule.setRoom(rs.getInt("RoomID"));
            schedule.setAssignedTo(rs.getInt("AssignedTo"));
            schedule.setTaskDate(rs.getDate("TaskDate"));
            schedule.setStat(rs.getInt("Stat"));
            return schedule;
        }
    }
}
