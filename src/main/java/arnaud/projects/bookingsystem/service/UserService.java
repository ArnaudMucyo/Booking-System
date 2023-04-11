package arnaud.projects.bookingsystem.service;

import arnaud.projects.bookingsystem.Models.RegistrationResponse;
import arnaud.projects.bookingsystem.Models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class UserService {

    final String username = "root";
    final String password = "Arno@7414";;
    final String url = "jdbc:mysql://localhost:3306/booking_system";

    Connection connection = null;
    private static final Logger Log = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Object registerUser(User user) throws SQLException {

        RegistrationResponse registrationResponse = new RegistrationResponse();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String checkUsername = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(checkUsername);
            stmt.setString(1, user.getUsername());
            ResultSet resultSet = stmt.executeQuery();

            try {

                if (resultSet.next()) {
                    //Response duplicationResponse = new Response();
                    registrationResponse.setStatus("DUPLICATION");
                    registrationResponse.setUsername("Username " + user.getUsername() + " exists");
                    Log.warn("Username " + user.getUsername() + " exists, please use another username");
                } else {
                    String insertUserQuery = "INSERT INTO users (firstname, lastname, username, password) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertUser = connection.prepareStatement(insertUserQuery);
                    insertUser.setString(1, user.getFirstname());
                    insertUser.setString(2, user.getLastname());
                    insertUser.setString(3, user.getUsername());
                    insertUser.setString(4, passwordEncoder.encode(user.getPassword()));
                    insertUser.execute();
                    Log.info("User " + user.getUsername() + " created successfully!");
                    //Response successResponse = new Response();
                    registrationResponse.setStatus("SUCCESS");
                    registrationResponse.setUsername(user.getUsername());
                    return registrationResponse;

                }

            }
            finally {
                resultSet.close();
            }
            
        }

        catch (SQLException e) {
            e.printStackTrace();
            Log.error("Error occurred: " + e.getMessage());
            registrationResponse.setStatus("FAILED");
            registrationResponse.setUsername("There was an error while registering user");
            return registrationResponse;
        }

        return registrationResponse;


    }
}
