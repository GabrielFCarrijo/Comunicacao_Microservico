package br.com.microservico.product_api.config.exception.interceptor;

import br.com.microservico.product_api.modules.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (isOptions(request)) {
            return true;
        }
        var authorization = request.getHeader(AUTHORIZATION);
        //jwtService.validateAuthorization(authorization);
        //request.setAttribute("serviceid", UUID.randomUUID().toString());
        return true;
    }

    private boolean isOptions(HttpServletRequest request) {
        return HttpMethod.OPTIONS.name().equals(request.getMethod());
    }
}