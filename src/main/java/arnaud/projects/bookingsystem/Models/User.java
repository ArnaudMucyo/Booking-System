package arnaud.projects.bookingsystem.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String firstname;
    @Column(length = 50)
    private String lastname;
    @Column(length = 50)
    private String username;
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String phone_number;
    @Column(length = 100)
    private String password;
}
