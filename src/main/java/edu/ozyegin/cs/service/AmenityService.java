package edu.ozyegin.cs.service;

import edu.ozyegin.cs.entity.Amenity;
import edu.ozyegin.cs.repository.AmenityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmenityService {

    int ADMIN = 2;
    private UserService userService;
    private AmenityRepository amenityRepository;

    public AmenityService(UserService userService, AmenityRepository amenityRepository) {
        this.userService = userService;
        this.amenityRepository = amenityRepository;
    }

    public boolean createAmenity(String amenityName, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            List<Amenity> amenities = this.amenityRepository.findAll();
            for (Amenity amenity: amenities) {
                if (amenity.getAmenityName().equals(amenityName)){
                    return false;
                }
            }
            this.amenityRepository.save(new Amenity(amenityName));
            return true;
        }
        return false;
    }

    public boolean modifyAmenity(String amenityName, int amenityId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            this.amenityRepository.update(new Amenity(amenityId,amenityName));
            return true;
        }
        return false;
    }

    public boolean deleteAmenity(int amenityId, int callerUserId) {
        int userType = this.userService.getUserType(callerUserId);
        if ( userType == ADMIN){
            this.amenityRepository.deleteById(amenityId);
            return true;
        }
        return false;
    }

    public List<Amenity> getAllAmenities() {
        return this.amenityRepository.findAll();
    }
}
