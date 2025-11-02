package vn.edu.ou.shuttletracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Kích hoạt WebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Đây là "chủ đề" (topic) mà server sẽ gửi tin nhắn đến.
        // Trình duyệt sẽ lắng nghe trên "/topic"
        config.enableSimpleBroker("/topic");
        
        // Đây là tiền tố cho các tin nhắn từ trình duyệt gửi lên (nếu có)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đây là "cổng" (endpoint) mà trình duyệt sẽ kết nối WebSocket vào.
        // Chúng ta sẽ kết nối tới "/ws"
        registry.addEndpoint("/ws").withSockJS();
    }
}