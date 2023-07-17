package edu.ozyegin.cs.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "bookingstatustypes")
public class BookingStatusType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeID")
    private int typeId;

    @Column(name = "TypeName", nullable = false)
    private String typeName;
}
