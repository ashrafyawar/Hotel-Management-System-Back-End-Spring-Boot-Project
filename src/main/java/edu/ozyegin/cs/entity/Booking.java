package edu.ozyegin.cs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID")
    private int bookingId;

    @Column(name = "RoomID", nullable = false)
    private int room;

    @Column(name = "GuestID", nullable = false)
    private int guest;

    @Column(name = "StartDate", nullable = false)
    private Date startDate;

    @Column(name = "EndDate", nullable = false)
    private Date endDate;

    @Column(name = "Price")
    private int price;

    @Column(name = "IsPayed", nullable = false)
    private boolean isPayed;

    @Column(name = "PeopleCount")
    private int peopleCount;

    @Column(name = "Stat", nullable = false)
    private int stat;

    public Booking(int room, int guest, Date startDate, Date endDate, int price, boolean isPayed, int peopleCount, int stat) {
        this.room = room;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.isPayed = isPayed;
        this.peopleCount = peopleCount;
        this.stat = stat;
    }
}
