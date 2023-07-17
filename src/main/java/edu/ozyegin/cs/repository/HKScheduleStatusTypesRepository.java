package edu.ozyegin.cs.repository;

import edu.ozyegin.cs.entity.HKScheduleStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HKScheduleStatusTypesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<HKScheduleStatusType> findAll() {
        String sql = "SELECT * FROM HKScheduleStatusTypes";
        return jdbcTemplate.query(sql, new HKScheduleStatusTypeRowMapper());
    }

    public HKScheduleStatusType findById(int typeId) {
        String sql = "SELECT * FROM HKScheduleStatusTypes WHERE TypeID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {typeId}, new HKScheduleStatusTypeRowMapper());
    }

    public void save(HKScheduleStatusType statusType) {
        String sql = "INSERT INTO HKScheduleStatusTypes(TypeName) VALUES(?)";
        jdbcTemplate.update(sql, statusType.getTypeName());
    }

    public void update(HKScheduleStatusType statusType) {
        String sql = "UPDATE HKScheduleStatusTypes SET TypeName = ? WHERE TypeID = ?";
        jdbcTemplate.update(sql, statusType.getTypeName(), statusType.getTypeId());
    }

    public void deleteById(int typeId) {
        String sql = "DELETE FROM HKScheduleStatusTypes WHERE TypeID = ?";
        jdbcTemplate.update(sql, typeId);
    }

    class HKScheduleStatusTypeRowMapper implements RowMapper<HKScheduleStatusType> {
        @Override
        public HKScheduleStatusType mapRow(ResultSet rs, int rowNum) throws SQLException {
            HKScheduleStatusType statusType = new HKScheduleStatusType();
            statusType.setTypeId(rs.getInt("TypeID"));
            statusType.setTypeName(rs.getString("TypeName"));
            return statusType;
        }
    }
}
