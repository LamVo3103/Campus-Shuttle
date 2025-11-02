package vn.edu.ou.shuttletracker.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import vn.edu.ou.shuttletracker.model.User;
import vn.edu.ou.shuttletracker.repository.UserRepository;

import java.util.Collections;

@Service // Báo cho Spring biết đây là một Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Spring sẽ tự động "tiêm" UserRepository vào đây
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Chúng ta sẽ cho phép đăng nhập bằng studentId
        User user = userRepository.findByStudentId(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("Không tìm thấy user với ID: " + username));

        // Trả về một đối tượng UserDetails mà Spring Security hiểu được
        return new org.springframework.security.core.userdetails.User(
            user.getStudentId(),
            user.getPasswordHash(), // Lấy mật khẩu đã mã hóa từ CSDL
            // Lấy quyền (role) từ CSDL
            Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()))
        );
    }
}