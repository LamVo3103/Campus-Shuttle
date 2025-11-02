package vn.edu.ou.shuttletracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Bus {

    @Id
    private String busId;
    
    private String licensePlate; 
    private int capacity;        
    private String status;       
    
    private double currentLatitude;
    private double currentLongitude;
    
    private String eta;
    
    private String crowdedness; // <-- THÊM DÒNG NÀY
}