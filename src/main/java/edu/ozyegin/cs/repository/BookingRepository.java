package edu.ozyegin.cs.repository;

import edu.ozyegin.cs.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class BookingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Booking> findAll() {
        String sql = "SELECT * FROM Bookings";
        return jdbcTemplate.query(sql, new BookingRowMapper());
    }

    public Booking findById(int bookingId) {
        String sql = "SELECT * FROM Bookings WHERE BookingID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {bookingId}, new BookingRowMapper());
    }

    public void save(Booking booking) {
        String sql = "INSERT INTO Bookings(RoomID, GuestID, StartDate, EndDate, Price, IsPayed, PeopleCount, Stat) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, booking.getRoom(), booking.getGuest(), booking.getStartDate(), booking.getEndDate(), booking.getPrice(), booking.isPayed(), booking.getPeopleCount(), booking.getStat());
    }
    public void update(Booking booking) {
        String sql = "UPDATE Bookings SET RoomID = ?, GuestID = ?, StartDate = ?, EndDate = ?, Price = ?, IsPayed = ?, PeopleCount = ?, Stat = ? WHERE BookingID = ?";
        jdbcTemplate.update(sql, booking.getRoom(), booking.getGuest(), booking.getStartDate(), booking.getEndDate(), booking.getPrice(), booking.isPayed(), booking.getPeopleCount(), booking.getStat(), booking.getBookingId());
    }

    public void updateDates(int bookingId, Date startDate, Date endDate) {
        String sql = "UPDATE Bookings SET StartDate = ?, EndDate = ? WHERE BookingID = ?";
        jdbcTemplate.update(sql, startDate,endDate,bookingId);
    }

    public void updatePrice(int newPrice, int bookingId) {
        String sql = "UPDATE Bookings SET Price = ? WHERE BookingID = ?";
        jdbcTemplate.update(sql, newPrice,bookingId);
    }

    public void updateIsPayedToTrue(int bookingId) {
        String sql = "UPDATE Bookings SET IsPayed = 1 WHERE BookingID = ?";
        jdbcTemplate.update(sql, bookingId);
    }

    public void updateStatAsCheckedIn(int bookingId,Date startDate) {
        String sql = "UPDATE Bookings SET StartDate = ?, Stat = 1 WHERE BookingID = ?";
        jdbcTemplate.update(sql, startDate,bookingId);
    }

    public void updateStatAsCheckedOut(int bookingId,Date endDate) {
        String sql = "UPDATE Bookings SET EndDate = ?, Stat = 2 WHERE BookingID = ?";
        jdbcTemplate.update(sql, endDate,bookingId);
    }

    public void deleteById(int bookingId) {
        String sql = "DELETE FROM Bookings WHERE BookingID = ?";
        jdbcTemplate.update(sql, bookingId);
    }

    public Integer countAll() {
        String sql = "SELECT COUNT(*) FROM Bookings";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public int countAllCancelled() {
        String sql = "SELECT COUNT(*) FROM Bookings WHERE Stat= 4";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    public Double averageBookingPrice(int roomId) {
        String sql = "SELECT AVG(Price) FROM Bookings WHERE RoomID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomId}, Double.class);
    }

    public Integer userWithMostBookingsForRoomId(int roomId) {
        String sql = "SELECT GuestID\n" +
                "FROM Bookings\n" +
                "WHERE RoomID = ?\n" +
                "GROUP BY GuestID\n" +
                "ORDER BY COUNT(*) DESC\n" +
                "LIMIT 1;";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomId}, Integer.class);
    }

    public Integer userIdWithLongestDurationInRoom(int roomId) {
        String sql = "SELECT GuestID\n" +
                "FROM Bookings\n" +
                "WHERE RoomID = ?\n" +
                "GROUP BY GuestID\n" +
                "ORDER BY SUM(DATEDIFF(EndDate, StartDate)) DESC\n" +
                "LIMIT 1;";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomId}, Integer.class);
    }

    public Double averagePriceOfRoomsWithMostBookings(int t) {
        String sql = "SELECT AVG(b.Price) as 'Average Price'\n" +
                "FROM Bookings b\n" +
                "INNER JOIN Rooms r ON r.RoomID = b.RoomID\n" +
                "WHERE DATEDIFF(b.EndDate, b.StartDate) >= ?\n" +
                "GROUP BY r.RoomID\n" +
                "HAVING COUNT(b.BookingID) = (SELECT COUNT(BookingID) as 'Booking Count' FROM Bookings WHERE DATEDIFF(EndDate, StartDate) >= ? ORDER BY 'Booking Count' DESC LIMIT 1);";
        return jdbcTemplate.queryForObject(sql, new Object[]{t,t}, Double.class);
    }

    public void cancelBooking(int bookingId) {
        String sql = "UPDATE Bookings SET Stat = 4 WHERE BookingID = ?";
        jdbcTemplate.update(sql, bookingId);
    }

    class BookingRowMapper implements RowMapper<Booking> {

        @Override
        public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
            Booking booking = new Booking();
            booking.setBookingId(rs.getInt("BookingID"));
            booking.setRoom(rs.getInt("RoomID"));
            booking.setGuest(rs.getInt("GuestID"));
            booking.setStartDate(rs.getDate("StartDate"));
            booking.setEndDate(rs.getDate("EndDate"));
            booking.setPrice(rs.getInt("Price"));
            booking.setPayed(rs.getBoolean("IsPayed"));
            booking.setPeopleCount(rs.getInt("PeopleCount"));
            booking.setStat(rs.getInt("Stat"));
            return booking;
        }
    }
}
