package edu.ozyegin.cs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private int userId;

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "UserPassword", nullable = false)
    private String password;

    @Column(name = "UserSalary")
    private int userSalary;

    @Column(name = "UserType", nullable = false)
    private int userType;

    public User(String username, String password, int userSalary, int userType) {
        this.username = username;
        this.password = password;
        this.userSalary = userSalary;
        this.userType = userType;
    }
}