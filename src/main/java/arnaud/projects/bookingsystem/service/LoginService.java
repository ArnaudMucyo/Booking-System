package arnaud.projects.bookingsystem.service;

import arnaud.projects.bookingsystem.Models.User;
import arnaud.projects.bookingsystem.Repository.UserRepository;
import arnaud.projects.bookingsystem.Models.CatchResponse;
import arnaud.projects.bookingsystem.Models.LoginResponse;
import arnaud.projects.bookingsystem.Utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger Log = LoggerFactory.getLogger(LoginService.class);

    public boolean usernameExists(String username) {
        User user = userRepository.checkUsername(username);
        return user != null;
    }

    public boolean verifyPassword(String username, String password) {
        String storedPassword = userRepository.checkPassword(username);
        return passwordEncoder.matches(password, storedPassword);
    }

    public Object userLogin(User user) {

        LoginResponse loginResponse = new LoginResponse();

        try {

            if (usernameExists(user.getUsername())) {
                if (verifyPassword(user.getUsername(), user.getPassword())) {
                    String token = jwtUtil.generateToken(user.getUsername());
                    loginResponse.setStatus("SUCCESS");
                    loginResponse.setToken(token);
                    loginResponse.setMessage("Successful login");
                    LocalDateTime expirationTime = jwtUtil.extractExpiration(token).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                    loginResponse.setExpiration(expirationTime.format(formatter));
                    loginResponse.setExpiration("Token expires in 60 minutes");
                    Log.info("User " + user.getUsername() + " successfully logged in, token expires is valid for 60 minutes");
                }
                else {
                    loginResponse.setStatus("FAILED");
                    loginResponse.setMessage("Incorrect password. Please enter the correct password");
                    Log.error("Password entered is incorrect");
                }
            } else  {
                loginResponse.setStatus("FAILED");
                loginResponse.setMessage("Username provided does not exist. Please enter a valid username");
                Log.warn("Username " + user.getUsername() + " does not exist");
            }
            return loginResponse;
        } catch (Exception e) {
            Log.error("Error occurred : " + e.getMessage());
            CatchResponse catchResponse = new CatchResponse();
            catchResponse.setOperation("LOGIN");
            catchResponse.setMessage("Error occurred : " + e.getMessage());
            return catchResponse;
        }

    }
}



