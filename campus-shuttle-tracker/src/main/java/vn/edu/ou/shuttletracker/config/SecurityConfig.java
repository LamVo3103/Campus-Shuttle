package vn.edu.ou.shuttletracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Báo đây là file cấu hình
@EnableWebSecurity // Bật bảo mật web
public class SecurityConfig {

    // Bean này dùng để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean này cấu hình các quy tắc bảo mật (ai được vào đâu)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
            // Đảm bảo dòng này có đầy đủ "/login" và "/ws/**"
            .requestMatchers("/register", "/login", "/api/**", "/css/**", "/js/**", "/ws/**").permitAll()
            // Bất kỳ request nào khác đều yêu con`u phải xác thực (đăng nhập)
            .anyRequest().authenticated() 
)
            .formLogin(form -> form
                // Chỉ định trang đăng nhập của chúng ta là "/login"
                .loginPage("/login")
                // Khi đăng nhập thành công, chuyển hướng về trang chủ "/"
                .defaultSuccessUrl("/", true)
                .permitAll() // Tất cả mọi người đều được thấy trang login
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Đường dẫn để đăng xuất
                .logoutSuccessUrl("/login?logout") // Trang sau khi đăng xuất
                .permitAll()
            );

        return http.build();
    }
}