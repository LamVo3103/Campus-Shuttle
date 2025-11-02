package vn.edu.ou.shuttletracker.model; // Đảm bảo package đúng

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor; 
import lombok.AllArgsConstructor; 

@Entity
@Data
@NoArgsConstructor   // (Tạo constructor rỗng)
@AllArgsConstructor // (Tạo constructor đầy đủ)
public class Stop {

    @Id
    private String stopId;
    
    private String name;
    private double latitude;  // Vĩ độ
    private double longitude; // Kinh độ
    
}