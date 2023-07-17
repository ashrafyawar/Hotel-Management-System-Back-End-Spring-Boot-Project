package edu.ozyegin.cs.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "amenities")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AmenityID")
    private int amenityId;

    @Column(name = "AmenityName", nullable = false)
    private String amenityName;

    public Amenity(String amenityName){
        this.amenityName = amenityName;
    }
}
