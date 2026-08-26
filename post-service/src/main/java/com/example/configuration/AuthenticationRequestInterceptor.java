package com.example.configuration;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class AuthenticationRequestInterceptor {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attributes != null) {
                    // Lấy header Authorization từ request gốc của người dùng gửi lên post-service
                    String authorizationHeader = attributes.getRequest().getHeader("Authorization");

                    if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
                        // Đính kèm token đó vào request tiếp theo của Feign client
                        template.header("Authorization", authorizationHeader);
                    }
                }
            }
        };
    }
}
