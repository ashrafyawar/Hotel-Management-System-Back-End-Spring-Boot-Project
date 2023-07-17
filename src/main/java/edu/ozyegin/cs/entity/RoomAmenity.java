package edu.ozyegin.cs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity

@NoArgsConstructor
@Table(name = "roomamenities")
public class RoomAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomID", nullable = false)
    private int room;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AmenityID", nullable = false)
    private int amenity;

    public RoomAmenity(int room, int amenity) {
        this.room = room;
        this.amenity = amenity;
    }
}

