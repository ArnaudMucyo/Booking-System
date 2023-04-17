package arnaud.projects.bookingsystem.Models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponse {

    private String status;
    private String message;
    private String time;
}
