package vn.edu.ou.shuttletracker.repository; // Đảm bảo package đúng

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ou.shuttletracker.model.Stop; // Import model Stop

@Repository
public interface StopRepository extends JpaRepository<Stop, String> {
}