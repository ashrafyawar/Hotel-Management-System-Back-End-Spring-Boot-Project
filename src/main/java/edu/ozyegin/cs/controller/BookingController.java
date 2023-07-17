package edu.ozyegin.cs.controller;

import edu.ozyegin.cs.entity.Booking;
import edu.ozyegin.cs.requests.BookingRequest;
import edu.ozyegin.cs.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {


    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/make_booking")
    public ResponseEntity<Boolean> makeBooking(@RequestBody BookingRequest bookingRequest) {
        boolean success = bookingService.makeBooking(bookingRequest.getUserId(), bookingRequest.getDayStart(), bookingRequest.getDayEnd(),
                bookingRequest.getPrice(),bookingRequest.getGuestCount(), bookingRequest.getCallerUserId(), bookingRequest.getRoomIdList());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/cancel_booking")
    public ResponseEntity<Boolean> cancelBooking(@RequestBody BookingRequest bookingRequest) {
        boolean success = bookingService.cancelBooking(bookingRequest.getBookingId(), bookingRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/modify/change_date")
    public ResponseEntity<Boolean> changeBookingDate(@RequestBody BookingRequest bookingRequest) {
        boolean success = bookingService.changeBookingDate(bookingRequest.getBookingId(), bookingRequest.getDayStart(),
                bookingRequest.getDayEnd(), bookingRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/modify/change_price")
    public ResponseEntity<Boolean> changeBookingPrice(@RequestBody BookingRequest bookingRequest) {
        boolean success = bookingService.changeBookingPrice(bookingRequest.getBookingId(), bookingRequest.getNewPrice(),
                bookingRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/get")
    public ResponseEntity<Booking> getBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.getBooking(bookingRequest.getBookingId(),bookingRequest.getCallerUserId());
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
    @PostMapping("/pay")
    public ResponseEntity<Boolean> pay(@RequestBody BookingRequest bookingRequest) {
        boolean success = bookingService.pay(bookingRequest.getBookingId(), bookingRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/check_in")
    public ResponseEntity<Boolean> checkIn(@RequestBody BookingRequest bookingRequest) {
        boolean success = bookingService.checkIn(bookingRequest.getBookingId(), bookingRequest.getCallerUserId(), bookingRequest.getDate());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/check_out")
    public ResponseEntity<Boolean> checkOut(@RequestBody BookingRequest bookingRequest) {
        boolean success = bookingService.checkOut(bookingRequest.getBookingId(), bookingRequest.getCallerUserId(), bookingRequest.getDate());
        return ResponseEntity.ok(success);
    }
}
