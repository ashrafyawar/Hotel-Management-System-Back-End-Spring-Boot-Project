package edu.ozyegin.cs.controller;

import edu.ozyegin.cs.entity.User;
import edu.ozyegin.cs.requests.UserRequest;
import edu.ozyegin.cs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody UserRequest userRequest) {
        boolean success = userService.createUser(userRequest.getUserName(),userRequest.getUserPassword(),
                userRequest.getSalary(), userRequest.getUserTypeId(), userRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @PostMapping("/modify/rename")
    public ResponseEntity<Boolean> renameUser(@RequestBody UserRequest userRequest) {
        boolean success = userService.renameUser(userRequest.getUserId(), userRequest.getNewName(), userRequest.getCallerUserId());
        return ResponseEntity.ok(success);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
