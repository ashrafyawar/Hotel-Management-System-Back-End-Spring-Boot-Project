package edu.ozyegin.cs.controller;

import edu.ozyegin.cs.entity.Amenity;
import edu.ozyegin.cs.requests.AmenityRequest;
import edu.ozyegin.cs.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/amenity")
public class AmenityController {

    private AmenityService amenityService;

    @Autowired
    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createAmenity(@RequestBody AmenityRequest amenityRequest) {
        boolean success = amenityService.createAmenity(amenityRequest.getAmenityName(), amenityRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/modify")
    public ResponseEntity<Boolean> modifyAmenity(@RequestBody AmenityRequest amenityRequest) {
        boolean success = amenityService.modifyAmenity(amenityRequest.getAmenityName(), amenityRequest.getAmenityId(), amenityRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteAmenity(@RequestBody AmenityRequest amenityRequest) {
        boolean success = amenityService.deleteAmenity(amenityRequest.getAmenityId(), amenityRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Amenity>> getAllAmenities() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities);
    }
}
