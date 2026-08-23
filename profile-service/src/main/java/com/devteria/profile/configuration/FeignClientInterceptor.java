package com.devteria.profile.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor{
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Lấy thông tin của request hiện tại đang gọi vào service
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // Lấy ra header Authorization (chứa token)
            String authorizationHeader = request.getHeader("Authorization");

            // Nếu có token, tiến hành gắn vào request của Feign Client
            if (StringUtils.hasText(authorizationHeader)) {
                requestTemplate.header("Authorization", authorizationHeader);
            }
        }
    }
}
