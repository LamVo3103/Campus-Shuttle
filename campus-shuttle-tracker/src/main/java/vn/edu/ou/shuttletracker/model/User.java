package vn.edu.ou.shuttletracker.model; // Đảm bảo package đúng

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data; 

@Entity 
@Data 
@Table(name = "users") 
public class User {

    @Id 
    private String userId;

    private String studentId;
    private String name;
    private String email;
    private String passwordHash; 
    private String role; 
}