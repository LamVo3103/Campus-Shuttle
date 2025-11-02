package vn.edu.ou.shuttletracker.repository; // Đảm bảo package đúng

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ou.shuttletracker.model.User; // Import model User
import java.util.Optional;

@Repository 
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByStudentId(String studentId);
}