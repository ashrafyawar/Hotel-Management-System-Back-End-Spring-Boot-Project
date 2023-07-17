package edu.ozyegin.cs.service;

import edu.ozyegin.cs.entity.User;
import edu.ozyegin.cs.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    int ADMIN = 2;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(String userName,String userPassword,int salary, int userTypeId, int callerUserId) {
        int userType = this.getUserType(callerUserId);
        if ( userType == ADMIN && userTypeId != ADMIN){ // if the authorized user is admin and also the new user is not admin type.
            List<User> users = this.userRepository.findAll();
            for (User user: users) {
                if (user.getUsername().equals(userName) && user.getUserType() == userTypeId){
                    return false;
                }
            }
            this.userRepository.save(new User(userName,userPassword,salary,userTypeId));
            return true;
        }
        return false;
    }

    public boolean renameUser(int userId, String newName, int callerUserId) {
        int userType = this.getUserType(callerUserId);
        if ( userType == ADMIN){
            this.userRepository.updateName(userId,newName);
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {

        return this.userRepository.findAll();
    }

    public int getUserType(int callerUserId) {
        return this.userRepository.findById(callerUserId).getUserType();
    }
}
