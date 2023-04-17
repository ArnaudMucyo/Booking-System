package arnaud.projects.bookingsystem.Repository;

import arnaud.projects.bookingsystem.Models.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Transactional
    @Modifying
    @Query(value = "insert into booking (firstname,lastname,email,date,phone_number) values (:firstname,:lastname,:email,:date,:phone_number)",nativeQuery = true)
    void createBooking(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("email") String email,
                       @Param("date") Date date, @Param("phone_number") String phone_number);
}
