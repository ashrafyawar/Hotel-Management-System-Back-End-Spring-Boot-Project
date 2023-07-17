package edu.ozyegin.cs.controller;

import edu.ozyegin.cs.entity.Room;
import edu.ozyegin.cs.requests.StatisticsRequest;
import edu.ozyegin.cs.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {


    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/room/bookings")
    public ResponseEntity<Integer> getBookingCount(@RequestBody StatisticsRequest statisticsRequest) {
        int count = statisticsService.getBookingCount(statisticsRequest.getRoomId(), statisticsRequest.getCallerUserId());
        return ResponseEntity.ok(count);
    }

    @PostMapping("/room/bookings/conversion_rate")
    public ResponseEntity<Double> getConversionRate(@RequestBody StatisticsRequest statisticsRequest) {
        double ratio = statisticsService.getConversionRate(statisticsRequest.getRoomId(), statisticsRequest.getCallerUserId());
        return ResponseEntity.ok(ratio);
    }

    @PostMapping("/room/average_price")
    public ResponseEntity<Double> getAveragePrice(@RequestBody StatisticsRequest statisticsRequest) {
        double price = statisticsService.getAveragePrice(statisticsRequest.getRoomId(), statisticsRequest.getCallerUserId());
        return ResponseEntity.ok(price);
    }

    @PostMapping("/room/max_user_bookings")
    public ResponseEntity<Integer> getUserIdWithMostBookingsForRoom(@RequestBody StatisticsRequest statisticsRequest) {
        return ResponseEntity.ok(statisticsService.getUserIdWithMostBookingsForRoom(statisticsRequest.getRoomId(), statisticsRequest.getCallerUserId()));
    }

    @PostMapping("/room/max_user_duration")
    public ResponseEntity<Integer> getUserIdWithLongestDurationInRoom(@RequestBody StatisticsRequest statisticsRequest) {
        return ResponseEntity.ok(statisticsService.getUserIdWithLongestDurationInRoom(statisticsRequest.getRoomId(), statisticsRequest.getCallerUserId()));
    }

    @PostMapping("/room/amenity_count")
    public ResponseEntity<List<Room>> getRoomsWithAtLeastKAmenities(@RequestBody StatisticsRequest statisticsRequest) {
        return ResponseEntity.ok(statisticsService.getRoomsWithAtLeastKAmenities(statisticsRequest.getRoomId(), statisticsRequest.getK(), statisticsRequest.getCallerUserId()));
    }

    @PostMapping("/housekeeping/count_clean")
    public ResponseEntity<Integer> getTotalNumberOfFinishedHousekeepingTasks(@RequestBody StatisticsRequest statisticsRequest) {
        return ResponseEntity.ok(statisticsService.getTotalNumberOfFinishedHousekeepingTasks(statisticsRequest.getCallerUserId()));
    }

    @PostMapping("/housekeeping/max_unique")
    public ResponseEntity<Integer> getUserIdWithMostCleanedRooms(@RequestBody StatisticsRequest statisticsRequest) {
        return ResponseEntity.ok(statisticsService.getUserIdWithMostCleanedRooms(statisticsRequest.getCallerUserId()));
    }

    @PostMapping("/housekeeping/max_unfinished")
    public ResponseEntity<Integer> getHousekeeperWithMostPendingTasks(@RequestBody StatisticsRequest statisticsRequest) {
        int userId = statisticsService.getHousekeeperWithMostPendingTasks(statisticsRequest.getCallerUserId());
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @PostMapping("/housekeeping/with_most_housekeepers")
    public ResponseEntity<List<Integer>> getRoomsWithHighestNumberOfDifferentHousekeepers(@RequestBody StatisticsRequest statisticsRequest) {
        List<Integer> roomIds = statisticsService.getRoomsWithHighestNumberOfDifferentHousekeepers(statisticsRequest.getCallerUserId());
        return new ResponseEntity<>(roomIds, HttpStatus.OK);
    }

    @PostMapping("/bookings/price/amenity")
    public ResponseEntity<Double> getAverageBookingPriceForRoomsWithAtLeastKAmenities(@RequestBody StatisticsRequest statisticsRequest) {
        double averagePrice = statisticsService.getAverageBookingPriceForRoomsWithAtLeastKAmenities(statisticsRequest.getCallerUserId(), statisticsRequest.getK());
        return new ResponseEntity<>(averagePrice, HttpStatus.OK);
    }
    @PostMapping("/bookings/max_room/price")
    public ResponseEntity<Double> getAveragePriceOfRoomsWithMostBookings(@RequestBody StatisticsRequest statisticsRequest) {
        return ResponseEntity.ok(statisticsService.getAveragePriceOfRoomsWithMostBookings(statisticsRequest.getCallerUserId(), statisticsRequest.getT()));
    }

    @PostMapping("/amenity/max")
    public ResponseEntity<List<Integer>> getIdsOfMostCommonAmenities(@RequestBody StatisticsRequest statisticsRequest) {
        return ResponseEntity.ok(statisticsService.getIdsOfMostCommonAmenities(statisticsRequest.getCallerUserId()));
    }

    @PostMapping("/amenity/difference")
    public ResponseEntity<Double> getPriceDifferenceBetweenRoomsWithMostAndSecondMostCommonAmenities(@RequestBody StatisticsRequest statisticsRequest) {
        return ResponseEntity.ok(statisticsService.getPriceDifferenceBetweenRoomsWithMostAndSecondMostCommonAmenities(statisticsRequest.getCallerUserId()));
    }
}