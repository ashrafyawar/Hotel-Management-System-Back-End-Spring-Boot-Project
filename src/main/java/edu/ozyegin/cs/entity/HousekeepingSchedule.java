package edu.ozyegin.cs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "housekeepingschedules")
public class HousekeepingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScheduleID")
    private int scheduleId;

    @Column(name = "RoomID", nullable = false)
    private int room;

    @Column(name = "AssignedTo")
    private int assignedTo;

    @Column(name = "TaskDate", nullable = false)
    private Date taskDate;

    @Column(name = "Stat", nullable = false)
    private int stat;

    public HousekeepingSchedule(int room, int assignedTo, Date taskDate, int stat) {
        this.room = room;
        this.assignedTo = assignedTo;
        this.taskDate = taskDate;
        this.stat = stat;
    }
}
