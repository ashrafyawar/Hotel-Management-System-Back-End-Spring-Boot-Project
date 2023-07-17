package edu.ozyegin.cs.repository;

import edu.ozyegin.cs.entity.BookingStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookingStatusTypesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BookingStatusType> findAll() {
        String sql = "SELECT * FROM BookingStatusTypes";
        return jdbcTemplate.query(sql, new BookingStatusTypeRowMapper());
    }

    public BookingStatusType findById(int typeId) {
        String sql = "SELECT * FROM BookingStatusTypes WHERE TypeID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {typeId}, new BookingStatusTypeRowMapper());
    }

    public void save(BookingStatusType statusType) {
        String sql = "INSERT INTO BookingStatusTypes(TypeName) VALUES(?)";
        jdbcTemplate.update(sql, statusType.getTypeName());
    }

    public void update(BookingStatusType statusType) {
        String sql = "UPDATE BookingStatusTypes SET TypeName = ? WHERE TypeID = ?";
        jdbcTemplate.update(sql, statusType.getTypeName(), statusType.getTypeId());
    }

    public void deleteById(int typeId) {
        String sql = "DELETE FROM BookingStatusTypes WHERE TypeID = ?";
        jdbcTemplate.update(sql, typeId);
    }

    class BookingStatusTypeRowMapper implements RowMapper<BookingStatusType> {
        @Override
        public BookingStatusType mapRow(ResultSet rs, int rowNum) throws SQLException {
            BookingStatusType statusType = new BookingStatusType();
            statusType.setTypeId(rs.getInt("TypeID"));
            statusType.setTypeName(rs.getString("TypeName"));
            return statusType;
        }
    }
}

