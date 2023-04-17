package arnaud.projects.bookingsystem.Controller;

import arnaud.projects.bookingsystem.Models.*;
import arnaud.projects.bookingsystem.service.BookingService;
import arnaud.projects.bookingsystem.service.LoginService;
import arnaud.projects.bookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(value = "/booking")
public class AppController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/register/user")
    public ResponseEntity<Object> userRegistration(@RequestBody User user) throws SQLException {

        Object result = userService.registerUser(user);

        if (result instanceof RegistrationResponse registrationResponse) {

            switch (registrationResponse.getStatus()) {
                case "SUCCESS" -> {
                    return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
                }
                case "DUPLICATION" -> {
                    return new ResponseEntity<>(registrationResponse, HttpStatus.CONFLICT);
                }
                case "FAILED" -> {
                    return new ResponseEntity<>(registrationResponse, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        Object result =  loginService.userLogin(user);

        if (result instanceof LoginResponse loginResponse){
            if (loginResponse.getStatus().equals("SUCCESS")) {
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);
            }
            else return new ResponseEntity<>(loginResponse,HttpStatus.UNAUTHORIZED);
        }
        else if (result instanceof CatchResponse catchResponse) {
            return new ResponseEntity<>(catchResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else return new ResponseEntity<>("An error occurred",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("new")
    public ResponseEntity<Object> newBooking(@RequestBody Booking booking){
        Object result = bookingService.createBooking(booking);

        if (result instanceof BookingResponse bookingResponse){
            if (bookingResponse.getStatus().equals("SUCCESS")){
                return new ResponseEntity<>(bookingResponse,HttpStatus.CREATED);
            }
            else return new ResponseEntity<>(bookingResponse,HttpStatus.UNAUTHORIZED);
        }
        else if (result instanceof CatchResponse catchResponse){
            return new ResponseEntity<>(catchResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else return new ResponseEntity<>("An error occurred ",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
