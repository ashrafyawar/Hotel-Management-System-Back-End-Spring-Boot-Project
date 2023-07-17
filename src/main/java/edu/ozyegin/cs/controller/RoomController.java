package edu.ozyegin.cs.controller;

import edu.ozyegin.cs.entity.Room;
import edu.ozyegin.cs.requests.RoomRequest;
import edu.ozyegin.cs.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")

public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createRoom(@RequestBody RoomRequest roomRequest) {
        boolean success = roomService.createRoom(roomRequest.getRoomName(), roomRequest.getRoomTypeId(), roomRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/modify/rename")
    public ResponseEntity<Boolean> renameRoom(@RequestBody RoomRequest roomRequest) {
        boolean success = roomService.renameRoom(roomRequest.getRoomId(), roomRequest.getNewName(), roomRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/modify/change_type")
    public ResponseEntity<Boolean> changeRoomType(@RequestBody RoomRequest roomRequest) {
        boolean success = roomService.changeRoomType(roomRequest.getRoomId(), roomRequest.getRoomTypeId(), roomRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/modify/delete")
    public ResponseEntity<Boolean> deleteRoom(@RequestBody RoomRequest roomRequest) {
        boolean success = roomService.deleteRoom(roomRequest.getRoomId(), roomRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/amenity/add")
    public ResponseEntity<Boolean> addAmenityToRoom(@RequestBody RoomRequest roomRequest) {
        boolean success = roomService.addAmenityToRoom(roomRequest.getRoomId(), roomRequest.getAmenityId(), roomRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/amenity/remove")
    public ResponseEntity<Boolean> removeAmenityFromRoom(@RequestBody RoomRequest roomRequest) {
        boolean success = roomService.removeAmenityFromRoom(roomRequest.getRoomId(), roomRequest.getAmenityId(), roomRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/get_available_for_date")
    public ResponseEntity<List<Room>> getAvailableRoomsForDate(@RequestBody RoomRequest roomRequest) {
        List<Room> availableRooms = roomService.getAvailableRoomsForDate(roomRequest.getStartDate(), roomRequest.getEndDate());
        return ResponseEntity.ok(availableRooms);
    }
}

