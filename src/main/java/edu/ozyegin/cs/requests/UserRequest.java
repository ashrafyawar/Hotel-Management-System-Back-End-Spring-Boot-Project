package edu.ozyegin.cs.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    String userName;
    String userPassword;
    Integer salary;
    int userId;
    int userTypeId;
    String newName;
    int callerUserId;
}
