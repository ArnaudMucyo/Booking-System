package arnaud.projects.bookingsystem.Repository;

import arnaud.projects.bookingsystem.Models.User;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user where username = :username",nativeQuery = true)
    User checkUsername(@Param("username") String username);

    @Query(value = "select password from user where username = :username",nativeQuery = true)
    String checkPassword(@Param("username") String username);
}
