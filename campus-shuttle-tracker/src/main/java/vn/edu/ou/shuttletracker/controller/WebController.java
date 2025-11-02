package vn.edu.ou.shuttletracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.edu.ou.shuttletracker.service.UserService;

@Controller
public class WebController {

    private final UserService userService;

    // Spring tự động "tiêm" UserService vào
    public WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Chào mừng đến với Campus Shuttle Tracker!");
        return "index";
    }

    // --- CÁC HÀM MỚI TỪ ĐÂY ---

    // Hiển thị trang login
    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về file login.html
    }

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String register() {
        return "register"; // Trả về file register.html
    }

    // Xử lý khi người dùng nhấn nút "Đăng ký"
    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("studentId") String studentId,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password")String password,
            RedirectAttributes redirectAttributes) { // Dùng để gửi thông báo

        try {
            userService.registerNewStudent(studentId, name, email, password);
            // Gửi thông báo thành công
            redirectAttributes.addFlashAttribute("success_message", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login"; // Chuyển hướng về trang login
        } catch (Exception e) {
            // Gửi thông báo lỗi
            redirectAttributes.addFlashAttribute("error_message", "Đăng ký thất bại: " + e.getMessage());
            return "redirect:/register"; // Quay lại trang đăng ký
        }
    }
}