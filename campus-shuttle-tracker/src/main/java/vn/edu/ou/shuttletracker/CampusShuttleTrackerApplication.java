package vn.edu.ou.shuttletracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // <-- BẠN BỊ THIẾU DÒNG NÀY

@EnableScheduling // Dòng này sẽ hết lỗi sau khi bạn thêm import ở trên
@SpringBootApplication
public class CampusShuttleTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusShuttleTrackerApplication.class, args);
    }

}