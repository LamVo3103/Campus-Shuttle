package vn.edu.ou.shuttletracker.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.edu.ou.shuttletracker.model.Bus;
import vn.edu.ou.shuttletracker.repository.BusRepository;

import java.util.Optional;
import java.util.Random; // Thư viện để tạo số ngẫu nhiên

@Service
public class BusSimulationService {

    private final BusRepository busRepository;
    private final SimpMessagingTemplate messagingTemplate; 
    private final Random random = new Random(); // Để tạo dữ liệu ngẫu nhiên

    // --- LOGIC MỚI CHO VIỆC DI CHUYỂN ---
    private boolean isMovingEast = true; // Biến cờ, true = đi về phía Đông, false = đi về phía Tây
    private final double WEST_LIMIT = 106.68900; // Giới hạn phía Tây
    private final double EAST_LIMIT = 106.69200; // Giới hạn phía Đông

    // Dữ liệu giả lập cho ETA
    private int etaIndex = 0;
    private final String[] etas = {"5 phút", "4 phút", "3 phút", "2 phút", "1 phút", "Sắp đến"};

    // --- LOGIC MỚI CHO CẢNH BÁO ---
    private int alertCounter = 0; // Bộ đếm để thỉnh thoảng gửi cảnh báo

    public BusSimulationService(BusRepository busRepository, SimpMessagingTemplate messagingTemplate) {
        this.busRepository = busRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // "fixedDelay = 5000" nghĩa là hàm này sẽ tự động chạy mỗi 5 giây
    @Scheduled(fixedDelay = 5000)
    public void simulateBusMovement() {
        Optional<Bus> busOpt = busRepository.findById("bus_01");
        
        if (busOpt.isPresent()) {
            Bus bus = busOpt.get();

            // --- 1. MÔ PHỎNG DI CHUYỂN (ĐÃ SỬA) ---
            if (isMovingEast) {
                // Đang đi về phía Đông (sang phải)
                double newLongitude = bus.getCurrentLongitude() + 0.0002;
                bus.setCurrentLongitude(newLongitude);
                if (newLongitude > EAST_LIMIT) {
                    isMovingEast = false; // Quay đầu!
                }
            } else {
                // Đang đi về phía Tây (sang trái)
                double newLongitude = bus.getCurrentLongitude() - 0.0002;
                bus.setCurrentLongitude(newLongitude);
                if (newLongitude < WEST_LIMIT) {
                    isMovingEast = true; // Quay đầu!
                }
            }
            
            // --- 2. MÔ PHỎNG ETA (Giữ nguyên) ---
            bus.setEta(etas[etaIndex]);
            etaIndex = (etaIndex + 1) % etas.length;
            
            // --- 3. (MỚI) MÔ PHỎNG ĐỘ ĐÔNG (F5) ---
            int passengerCount = random.nextInt(35); // Ngẫu nhiên 0-34 hành khách
            if (passengerCount < 10) {
                bus.setCrowdedness("Vắng");
            } else if (passengerCount < 25) {
                bus.setCrowdedness("Vừa phải");
            } else {
                bus.setCrowdedness("Đông!");
            }

            // 4. Lưu CSDL
            busRepository.save(bus);
            
            // 5. Gửi cập nhật xe bus
            // (Nó sẽ tự động gửi cả "crowdedness" vì nó là 1 phần của object Bus)
            messagingTemplate.convertAndSend("/topic/locations", bus);
            
            // --- 6. (MỚI) MÔ PHỎNG CẢNH BÁO DỊCH VỤ (F6) ---
            alertCounter++;
            // Cứ 10 lần cập nhật (50 giây) thì gửi 1 cảnh báo
            if (alertCounter == 10) {
                String alertMessage = "CẢNH BÁO: Xe " + bus.getLicensePlate() + " bị trễ 5 phút do kẹt xe tại Cổng Nguyễn Thông!";
                // Gửi cảnh báo đến một kênh (topic) KHÁC
                messagingTemplate.convertAndSend("/topic/alerts", alertMessage);
            } else if (alertCounter >= 20) {
                // Sau 20 lần (100 giây) thì xóa cảnh báo
                messagingTemplate.convertAndSend("/topic/alerts", ""); // Gửi tin nhắn rỗng để xóa banner
                alertCounter = 0; // Reset bộ đếm
            }
        }
    }
}