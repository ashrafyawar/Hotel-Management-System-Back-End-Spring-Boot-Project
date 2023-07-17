package edu.ozyegin.cs.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {
    String roomName;
    int roomTypeId;
    int roomId;
    String newName;
    int amenityId;
    String startDate;
    String endDate;
    int callerUserId;
}
