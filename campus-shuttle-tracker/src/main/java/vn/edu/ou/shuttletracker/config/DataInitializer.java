package vn.edu.ou.shuttletracker.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.edu.ou.shuttletracker.model.Bus;
import vn.edu.ou.shuttletracker.model.Stop;
import vn.edu.ou.shuttletracker.model.User;
import vn.edu.ou.shuttletracker.repository.BusRepository;
import vn.edu.ou.shuttletracker.repository.StopRepository;
import vn.edu.ou.shuttletracker.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BusRepository busRepository;
    private final StopRepository stopRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(BusRepository busRepository, StopRepository stopRepository,
                           UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.busRepository = busRepository;
        this.stopRepository = stopRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        
        // Thêm trạm dừng
        if (stopRepository.count() == 0) {
            stopRepository.save(new Stop("stop_01", "Cổng Võ Văn Tần", 10.77884, 106.69171));
            stopRepository.save(new Stop("stop_02", "Cổng Nguyễn Thông", 10.77926, 106.68971));
            stopRepository.save(new Stop("stop_03", "Nhà ăn", 10.77850, 106.69050));
        }

        // Thêm xe bus
        if (busRepository.count() == 0) {
            Bus bus1 = new Bus();
            bus1.setBusId("bus_01");
            bus1.setLicensePlate("51B-12345");
            bus1.setCapacity(30);
            bus1.setStatus("RUNNING");
            bus1.setCurrentLatitude(10.77800);
            
            // --- DÒNG ĐÃ SỬA LỖI ĐÂY ---
            bus1.setCurrentLongitude(106.69000); // Sửa 10. thành 106.
            
            busRepository.save(bus1);
        }
        
        // Thêm user test
        if (userRepository.count() == 0) {
            User testUser = new User();
            testUser.setUserId("testuser");
            testUser.setStudentId("123"); 
            testUser.setName("Sinh viên Test");
            testUser.setEmail("test@email.com");
            testUser.setPasswordHash(passwordEncoder.encode("123")); 
            testUser.setRole("STUDENT");
            userRepository.save(testUser);
        }
    }
}