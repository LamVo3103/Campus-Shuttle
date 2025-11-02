package vn.edu.ou.shuttletracker.repository; // Đảm bảo package đúng

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ou.shuttletracker.model.Bus; // Import model Bus

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {
}