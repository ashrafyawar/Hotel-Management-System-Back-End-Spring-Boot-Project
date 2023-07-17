package edu.ozyegin.cs.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomID")
    private int roomId;

    @Column(name = "RoomName", unique = true, nullable = false)
    private String roomName;

    @Column(name = "RoomType", nullable = false)
    private int roomType;

    public Room(String roomName, int roomType) {
        this.roomName = roomName;
        this.roomType = roomType;
    }
}