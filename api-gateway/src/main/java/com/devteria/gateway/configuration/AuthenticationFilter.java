package com.devteria.gateway.configuration;

import com.devteria.gateway.dto.ApiResponse;
import com.devteria.gateway.dto.request.IntrospectRequest;
import com.devteria.gateway.dto.response.IntrospectResponse;
import com.devteria.gateway.service.IdentityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;
import org.springframework.http.server.reactive.ServerHttpResponse;
@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {

    IdentityService identityService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

        if(CollectionUtils.isEmpty(authHeader))
            return unauthenticated(exchange.getResponse());

        String token = authHeader.getFirst().replace("Bearer","").trim();

        log.info("token: {}",token);

        return identityService.introspectToken(
                IntrospectRequest.builder()
                        .token(token)
                        .build()
        ).flatMap(introspectResponseApiResponse -> {
            if(introspectResponseApiResponse.getResult().isValid())
                return chain.filter(exchange);
            else
                return unauthenticated(exchange.getResponse());


        }).onErrorResume(throwable -> {
            log.error("Lỗi thực sự khi kết nối tới Identity Service:", throwable);
            return unauthenticated(exchange.getResponse());
        });

    }

    @Override
    public int getOrder() {
        return 0;
    }
    Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<IntrospectResponse> body = ApiResponse.<IntrospectResponse>builder()
                .code(1064)
                .message("UNAUTHORIZED")
                .build();

        // 1. Set Status và Header (RẤT QUAN TRỌNG ĐỂ POSTMAN HIỂU ĐÂY LÀ JSON)
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // 2. Dùng ObjectMapper để biến Object thành JSON byte array
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Chuyển body thành mảng byte
            byte[] bytes = objectMapper.writeValueAsBytes(body);

            // Ghi mảng byte ra response
            return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));

        } catch (JsonProcessingException e) {
            log.error("Lỗi khi đóng gói JSON response", e);
            // Trả về response trống nếu quá trình ép kiểu bị sập
            return response.setComplete();
        }
    }
}
