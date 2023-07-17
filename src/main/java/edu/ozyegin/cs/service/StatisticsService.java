package edu.ozyegin.cs.service;

import edu.ozyegin.cs.entity.Booking;
import edu.ozyegin.cs.entity.Room;
import edu.ozyegin.cs.repository.BookingRepository;
import edu.ozyegin.cs.repository.HousekeepingSchedulesRepository;
import edu.ozyegin.cs.repository.RoomAmenityRepository;
import edu.ozyegin.cs.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {
    int HOUSEKEEPER = 4;
    int RECEPTIONIST = 3;
    int ADMIN = 2;
    private BookingRepository bookingRepository;
    private UserService userService;
    private HousekeepingSchedulesRepository housekeepingSchedulesRepository;
    private RoomRepository roomRepository;
    private RoomAmenityRepository roomAmenityRepository;

    public StatisticsService(BookingRepository bookingRepository, UserService userService, HousekeepingSchedulesRepository housekeepingSchedulesRepository, RoomRepository roomRepository, RoomAmenityRepository roomAmenityRepository) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.housekeepingSchedulesRepository = housekeepingSchedulesRepository;
        this.roomRepository = roomRepository;
        this.roomAmenityRepository = roomAmenityRepository;
    }

    public int getBookingCount(int roomId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        int roomCount = 0;

        if ( userType == ADMIN || userType == RECEPTIONIST){
            List<Booking> bookings = new ArrayList<>();
            bookings = this.bookingRepository.findAll();
            for (Booking booking: bookings) {
                if (booking.getRoom() == roomId){++roomCount;}
            }
            return roomCount;
        }
        return 0;
    }

    public double getConversionRate(int roomId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            int  bookingCount = this.bookingRepository.countAll();
            int cancelledCount = this.bookingRepository.countAllCancelled();
            return  (bookingCount - cancelledCount) / bookingCount;
        }
        return 0;
    }

    public double getAveragePrice(int roomId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.bookingRepository.averageBookingPrice(roomId);
        }
        return 0;
    }

    public Integer getUserIdWithMostBookingsForRoom(int roomId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.bookingRepository.userWithMostBookingsForRoomId(roomId);
        }
        return 0;
    }

    public Integer getUserIdWithLongestDurationInRoom(int roomId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.bookingRepository.userIdWithLongestDurationInRoom(roomId);
        }
        return 0;
    }

    public List<Room> getRoomsWithAtLeastKAmenities(int roomId, int k, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.roomRepository.getRoomDetailsOfAtleastKAmenities(roomId,k);
        }
        return null;
    }

    public Integer getTotalNumberOfFinishedHousekeepingTasks(int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.roomRepository.totalNumberOfFinishedHousekeepingTasks();
        }
        return 0;
    }

    public Integer getUserIdWithMostCleanedRooms(int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.roomRepository.userIdWithMostCleanedRooms();
        }
        return 0;
    }

    public int getHousekeeperWithMostPendingTasks(int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.housekeepingSchedulesRepository.housekeeperWithMostPendingTasks();
        }
        return 0;
    }

    public List<Integer> getRoomsWithHighestNumberOfDifferentHousekeepers(int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.housekeepingSchedulesRepository.roomsWithHighestNumberOfDifferentHousekeepers();
        }
        return null;
    }

    public double getAverageBookingPriceForRoomsWithAtLeastKAmenities(int callerUserId, int k) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
//            List<Double> doubles =  this.roomAmenityRepository.averageBookingPriceForRoomsWithAtLeastKAmenities(k);
            return this.roomAmenityRepository.averageBookingPriceForRoomsWithAtLeastKAmenities(k);
        }
        return 0;
    }

    public Double getAveragePriceOfRoomsWithMostBookings(int callerUserId, int t) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.bookingRepository.averagePriceOfRoomsWithMostBookings(t);
        }
        return 0.0;
    }

    public List<Integer> getIdsOfMostCommonAmenities(int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.roomAmenityRepository.idsOfMostCommonAmenities();
        }
        return null;
    }

    public Double getPriceDifferenceBetweenRoomsWithMostAndSecondMostCommonAmenities(int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);

        if ( userType == ADMIN || userType == RECEPTIONIST){
            return this.roomAmenityRepository.priceDifferenceBetweenRoomsWithMostAndSecondMostCommonAmenities();
        }
        return 0.0;
    }
}
