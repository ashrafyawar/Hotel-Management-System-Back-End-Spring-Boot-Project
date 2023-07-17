package edu.ozyegin.cs.service;

import edu.ozyegin.cs.entity.Booking;
import edu.ozyegin.cs.entity.HousekeepingSchedule;
import edu.ozyegin.cs.entity.Room;
import edu.ozyegin.cs.entity.RoomAmenity;
import edu.ozyegin.cs.repository.BookingRepository;
import edu.ozyegin.cs.repository.HousekeepingSchedulesRepository;
import edu.ozyegin.cs.repository.RoomAmenityRepository;
import edu.ozyegin.cs.repository.RoomRepository;
import edu.ozyegin.cs.requests.RoomMap;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    int ADMIN = 2;
    private UserService userService;
    private RoomRepository roomRepository;
    private RoomAmenityRepository roomAmenityRepository;
    private BookingRepository bookingRepository;
    private HousekeepingSchedulesRepository housekeepingSchedulesRepository;

    public RoomService(UserService userService, RoomRepository roomRepository, RoomAmenityRepository roomAmenityRepository
    , BookingRepository bookingRepository, HousekeepingSchedulesRepository housekeepingSchedulesRepository) {
        this.userService = userService;
        this.roomRepository = roomRepository;
        this.roomAmenityRepository = roomAmenityRepository;
        this.bookingRepository =  bookingRepository;
        this.housekeepingSchedulesRepository = housekeepingSchedulesRepository;
    }

    public boolean createRoom(String roomName, int roomTypeId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            List<Room> rooms = this.roomRepository.findAll();
            for (Room room: rooms) {
                if (room.getRoomName().equals(roomName) && room.getRoomType() == roomTypeId){ // if room name or room type is already.
                    return false;
                }
            }
            this.roomRepository.save(new Room(roomName,roomTypeId));
            return true;
        }
        return false;
    }

    public boolean renameRoom(int roomId, String newName, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            List<Room> rooms = this.roomRepository.findAll();
            for (Room room: rooms) {
                if (room.getRoomName().equals(newName)){
                    return false;
                }
            }
            this.roomRepository.update(newName, roomId);
            return true;
        }
        return false;
    }

    public boolean changeRoomType(int roomId, int roomTypeId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            this.roomRepository.updateRoomType(roomTypeId, roomId);
            return true;
        }
        return false;
    }

    public boolean deleteRoom(int roomId, int callerUserId) {

        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            this.roomAmenityRepository.deleteByRoomId(roomId);
            this.roomRepository.deleteById(roomId);
            return true;
        }

        return false;
    }

    public boolean addAmenityToRoom(int roomId, int amenityId, int callerUserId) {

        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            List<RoomAmenity> roomAmenities = this.roomAmenityRepository.findAll();
            for (RoomAmenity roomAmenity: roomAmenities) {
                if (roomAmenity.getRoom() == roomId && roomAmenity.getAmenity() == amenityId){
                    return false;
                }
            }
            this.roomAmenityRepository.save(new RoomAmenity(roomId,amenityId));
            return true;
        }
        return false;
    }

    public boolean removeAmenityFromRoom(int roomId, int amenityId, int callerUserId) {

        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            this.roomAmenityRepository.deleteByRoomIdAndAmenityId(roomId,amenityId);
            return true;
        }
        return false;
    }

    public List<Room> getAllRooms() {
        return this.roomRepository.findAll();
    }

    public List<Room> getAvailableRoomsForDate(String dayStart, String dayEnd) {
        Date startDate = new Date(Long.valueOf(dayStart));
        Date endDate = new Date(Long.valueOf(dayEnd));

        List<Booking> bookings  = this.bookingRepository.findAll();
        List<Room> resRooms = new ArrayList<>();
        List<RoomMap> roomMaps = new ArrayList<>();

        for (Booking booking: bookings) {
            RoomMap roomMap = new RoomMap();
            roomMap.setRoomId(this.roomRepository.findById(booking.getRoom()).getRoomId());
            roomMap.setStat((booking.getEndDate().compareTo(startDate) < 0 || booking.getStartDate().compareTo(endDate) > 0));
            roomMaps.add(roomMap);
        }
        List<Integer> roomIds = new ArrayList<>();

        roomMaps.stream()
                .collect(Collectors.groupingBy(RoomMap::getRoomId))
                .forEach((id, roomMapList) -> {
                    boolean stat = roomMapList.stream()
                            .map(RoomMap::getStat)
                            .reduce(true, (a, b) -> a && b);
                    if (stat) {
                        roomIds.add(id);
                    }
                });
        // if endDate of the booked room is before the required startDate OR
        // if startDate of the booked room is after the required endDate.
//        resRooms.add(this.roomRepository.findById(booking.getRoom()));
        for (Integer roomId: roomIds) {
            resRooms.add(this.roomRepository.findById(roomId));
        }
        return resRooms;
    }
}
