package vn.edu.ou.shuttletracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.ou.shuttletracker.model.Bus;
import vn.edu.ou.shuttletracker.model.Stop;
import vn.edu.ou.shuttletracker.repository.BusRepository;
import vn.edu.ou.shuttletracker.repository.StopRepository;

import java.util.List;

@RestController // Quan trọng: Trả về JSON, không phải HTML
@RequestMapping("/api") // Tất cả các đường dẫn sẽ bắt đầu bằng /api
public class MapDataController {

    private final BusRepository busRepository;
    private final StopRepository stopRepository;

    public MapDataController(BusRepository busRepository, StopRepository stopRepository) {
        this.busRepository = busRepository;
        this.stopRepository = stopRepository;
    }

    @GetMapping("/stops")
    public List<Stop> getAllStops() {
        // Trả về danh sách tất cả các trạm
        return stopRepository.findAll();
    }

    @GetMapping("/buses")
    public List<Bus> getAllBuses() {
        // Trả về danh sách tất cả xe bus
        return busRepository.findAll();
    }
}