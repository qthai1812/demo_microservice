package com.devteria.identity.configuration;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class AuthenticationRequestIntercepter implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var authHeader = servletRequestAttributes.getRequest().getHeader("Authorization");

        if (StringUtils.hasText(authHeader)) {
            requestTemplate.header("Authorization", authHeader);
        }
    }
}
