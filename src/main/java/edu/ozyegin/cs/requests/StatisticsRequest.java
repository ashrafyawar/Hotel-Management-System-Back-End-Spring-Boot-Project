package edu.ozyegin.cs.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticsRequest {
    int roomId;
    int k;
    int T;
    int callerUserId;
}
