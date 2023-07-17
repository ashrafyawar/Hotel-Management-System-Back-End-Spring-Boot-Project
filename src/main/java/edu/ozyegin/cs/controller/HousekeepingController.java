package edu.ozyegin.cs.controller;

import edu.ozyegin.cs.entity.HousekeepingSchedule;
import edu.ozyegin.cs.entity.Room;
import edu.ozyegin.cs.requests.HouseKeepingRequest;
import edu.ozyegin.cs.service.HousekeepingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/housekeeping")
public class HousekeepingController {


    private final HousekeepingService housekeepingService;

    public HousekeepingController(HousekeepingService housekeepingService) {
        this.housekeepingService = housekeepingService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<Boolean> scheduleCleaning(@RequestBody HouseKeepingRequest houseKeepingRequest) {
        boolean success = housekeepingService.scheduleCleaning(houseKeepingRequest.getRoomId(), houseKeepingRequest.getUserId(),
                houseKeepingRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/clean")
    public ResponseEntity<Boolean> clean(@RequestBody HouseKeepingRequest houseKeepingRequest) {
        boolean success = housekeepingService.clean(houseKeepingRequest.getRoomId(), houseKeepingRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/schedule/get")
    public ResponseEntity<List<Room>> getCleaningSchedule(@RequestBody HouseKeepingRequest houseKeepingRequest) {
        List<Room> rooms = housekeepingService.getCleaningSchedule(houseKeepingRequest.getCallerUserId());
        return ResponseEntity.ok(rooms);
    }
}
