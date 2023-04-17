package arnaud.projects.bookingsystem.service;

import arnaud.projects.bookingsystem.Models.Booking;
import arnaud.projects.bookingsystem.Models.BookingResponse;
import arnaud.projects.bookingsystem.Models.CatchResponse;
import arnaud.projects.bookingsystem.Repository.BookingRepository;
import arnaud.projects.bookingsystem.Utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmailValidator emailValidator;

    public Object createBooking(Booking booking) {


        String firstname = booking.getFirstname();
        String lastname = booking.getLastname();
        String email = booking.getEmail();
        Date date = booking.getDate();
        String phone_number = booking.getPhone_number();

        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setTime(timestamp.toString());
        bookingResponse.setStatus("FAILED");
        bookingResponse.setMessage("An error occurred");

        try{

            if (firstname != null && !firstname.trim().isEmpty() && firstname != "") {
                if (EmailValidator.isEmailValid(email)){
                    if (date != null){
                        bookingRepository.createBooking(firstname.trim(),lastname.trim(),email.trim(),date,phone_number);
                        bookingResponse.setStatus("SUCCESS");
                        bookingResponse.setMessage("Booking successful");
                    }
                    else {
                        bookingResponse.setMessage("Please select a valid date");
                    }
                }
                else {
                    bookingResponse.setMessage("Please enter a valid email");
                }

            }
            else {
                bookingResponse.setMessage("Firstname field must not be empty. Please enter your firstname");
            }
        }
        catch (Exception e){
            CatchResponse catchResponse = new CatchResponse();
            catchResponse.setOperation("BOOKING");
            catchResponse.setMessage("Error occurred : " + e.getMessage());
            return catchResponse;
        }

        return bookingResponse;
    }
}
