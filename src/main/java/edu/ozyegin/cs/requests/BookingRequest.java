package edu.ozyegin.cs.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequest {

    int userId;
    String dayStart;
    String dayEnd;
    int price;
    int guestCount;
    int bookingId;
    int newPrice;
    String date;
    int callerUserId;
    List<Integer> roomIdList;
}
