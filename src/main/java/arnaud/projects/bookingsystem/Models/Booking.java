package arnaud.projects.bookingsystem.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50)
    private String firstname;
    @Column(length = 50)
    private String lastname;
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private Date date;
    @Column(length = 50)
    private String phone_number;

}
