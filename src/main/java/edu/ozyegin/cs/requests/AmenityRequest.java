package edu.ozyegin.cs.requests;

import lombok.Data;

@Data
public class AmenityRequest {
    int amenityId;
    String amenityName;
    int callerUserId;
}
