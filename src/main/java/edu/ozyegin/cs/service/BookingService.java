package edu.ozyegin.cs.service;

import edu.ozyegin.cs.entity.Booking;
import edu.ozyegin.cs.entity.Room;
import edu.ozyegin.cs.repository.BookingRepository;
import edu.ozyegin.cs.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    int RECEPTIONIST = 3;
    int ADMIN = 2;
    int GUEST = 1;
    private UserService userService;
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private RoomService roomService;


    public BookingService(UserService userService, BookingRepository bookingRepository, RoomRepository roomRepository, RoomService roomService) {
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    public boolean makeBooking(int userId, String dayStart, String dayEnd, int price,int guestCount, int callerUserId,List<Integer> roomIdList) {
        Date startDate = new Date(Long.valueOf(dayStart));
        Date endDate = new Date(Long.valueOf(dayEnd));
        int userType = this.userService.getUserType(callerUserId);

        if (userType == ADMIN || userType == GUEST) {
            // find the available rooms in a given date period
            List<Room> availableRooms  = this.roomService.getAvailableRoomsForDate(dayStart,dayEnd);
            List<Integer> availableRoomIds = availableRooms.stream().map(Room::getRoomId).collect(Collectors.toList());
            int roomCapacityCount = 0;

            if (availableRooms.containsAll(roomIdList)){
                for (Integer oneRoomId: roomIdList) {
                    roomCapacityCount += this.roomRepository.getRoomCapacity(this.roomRepository.findById(oneRoomId).getRoomType());
                }
                if (guestCount <= roomCapacityCount){
                    for (Integer oneRoom: roomIdList) {
                        this.bookingRepository.save(new Booking(oneRoom, userId, startDate, endDate, price, false, guestCount, 3));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean cancelBooking(int bookingId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            if (!this.bookingRepository.findById(bookingId).isPayed()){
                this.bookingRepository.cancelBooking(bookingId);
                return true;
            }
        }
        return false;
    }

    public boolean changeBookingDate(int bookingId, String dayStart, String dayEnd, int callerUserId) {
        Date startDate = new Date(Long.valueOf(dayStart));
        Date endDate = new Date(Long.valueOf(dayEnd));

        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN || userType == RECEPTIONIST){
            this.bookingRepository.updateDates(bookingId,startDate,endDate);
            return true;
        }
        return false;
    }

    public boolean changeBookingPrice(int bookingId, int newPrice, int callerUserId) {

        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN || userType == RECEPTIONIST){
            this.bookingRepository.updatePrice(newPrice,bookingId);
            return true;
        }
        return false;
    }

    public Booking getBooking(int bookingId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.bookingRepository.findById(bookingId);
        }
        return null;
    }

    public List<Booking> getAllBookings() {
        return this.bookingRepository.findAll();
    }

    public boolean pay(int bookingId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN || userType == RECEPTIONIST){
            this.bookingRepository.updateIsPayedToTrue(bookingId);
            return true;
        }
        return false;
    }

    public boolean checkIn(int bookingId, int callerUserId, String date) {
        Date startDate = new Date(Long.valueOf(date));

        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN || userType == RECEPTIONIST){
            this.bookingRepository.updateStatAsCheckedIn(bookingId,startDate);
            return true;
        }
        return false;
    }

    public boolean checkOut(int bookingId, int callerUserId, String date) {
        Date endDate = new Date(Long.valueOf(date));

        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN || userType == RECEPTIONIST){
            this.bookingRepository.updateStatAsCheckedOut(bookingId,endDate);
            return true;
        }
        return false;
    }
}
