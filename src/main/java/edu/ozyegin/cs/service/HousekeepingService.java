package edu.ozyegin.cs.service;

import edu.ozyegin.cs.entity.HousekeepingSchedule;
import edu.ozyegin.cs.entity.Room;
import edu.ozyegin.cs.repository.HousekeepingSchedulesRepository;
import edu.ozyegin.cs.repository.RoomRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HousekeepingService {
    int HOUSEKEEPER = 4;
    int RECEPTIONIST = 3;
    int ADMIN = 2;
    private HousekeepingSchedulesRepository housekeepingSchedulesRepository;
    private UserService userService;
    private RoomRepository roomRepository;

    public HousekeepingService(HousekeepingSchedulesRepository housekeepingSchedulesRepository,UserService userService,
                               RoomRepository roomRepository) {
        this.housekeepingSchedulesRepository = housekeepingSchedulesRepository;
        this.userService = userService;
        this.roomRepository = roomRepository;
    }
    public boolean scheduleCleaning(int roomId, int userId, int callerUserId) {

        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN || userType == RECEPTIONIST){
            this.housekeepingSchedulesRepository.save(new HousekeepingSchedule(roomId,userId,new Date(),1));
            return true;
        }
        return false;
    }

    public boolean clean(int roomId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN || userType == RECEPTIONIST){
            List<HousekeepingSchedule> housekeepingSchedules= this.housekeepingSchedulesRepository.findAll();

            for (HousekeepingSchedule houseKeepingSchedule: housekeepingSchedules) {
                if (houseKeepingSchedule.getRoom() == roomId && houseKeepingSchedule.getStat() == 1){
                    this.housekeepingSchedulesRepository.setHouseKeepingScheduleToInProgress(houseKeepingSchedule.getScheduleId());
                    this.housekeepingSchedulesRepository.setHouseKeepingScheduleToDone(houseKeepingSchedule.getScheduleId());
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public List<Room> getCleaningSchedule(int callerUserId) {

        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN || userType == RECEPTIONIST){
            List<HousekeepingSchedule> housekeepingSchedules =  this.housekeepingSchedulesRepository.findAllStat1();
            List<Integer> roomIds = new ArrayList<>();
            List<Room> rooms = new ArrayList<>();
            for (HousekeepingSchedule houseKeepingSchedule: housekeepingSchedules) {roomIds.add(houseKeepingSchedule.getRoom());}
            for (Integer roomId: roomIds) {rooms.add(this.roomRepository.findById(roomId));}
            return rooms;
        }
        return null;
    }
}
