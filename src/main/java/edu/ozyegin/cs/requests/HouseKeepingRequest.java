package edu.ozyegin.cs.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseKeepingRequest {
    int roomId;
    int userId;
    int callerUserId;
}
