package vn.edu.ou.shuttletracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.ou.shuttletracker.model.User;
import vn.edu.ou.shuttletracker.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Dùng để mã hóa mật khẩu

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewStudent(String studentId, String name, String email, String rawPassword) throws Exception {
        // Kiểm tra xem studentId đã tồn tại chưa
        Optional<User> existingUser = userRepository.findByStudentId(studentId);
        if (existingUser.isPresent()) {
            throw new Exception("Student ID đã tồn tại");
        }

        User newUser = new User();
        newUser.setUserId(studentId); // Tạm thời dùng studentId làm userId
        newUser.setStudentId(studentId);
        newUser.setName(name);
        newUser.setEmail(email);
        
        // Mã hóa mật khẩu trước khi lưu
        newUser.setPasswordHash(passwordEncoder.encode(rawPassword));
        
        newUser.setRole("STUDENT"); // Mặc định khi đăng ký là STUDENT

        return userRepository.save(newUser);
    }
}