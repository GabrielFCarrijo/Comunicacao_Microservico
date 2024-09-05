package br.com.microservico.product_api.config.exception.interceptor;

import br.com.microservico.product_api.config.exception.ValidationException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static br.com.microservico.product_api.config.exception.interceptor.RequestUtil.getCurrentRequest;

@Component
public class FeignClienteAuthInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "transaction_id";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        var createRequest = getCurrentRequest();
        requestTemplate.header(AUTHORIZATION, createRequest.getHeader(AUTHORIZATION));
        requestTemplate.header(TRANSACTION_ID, createRequest.getHeader(TRANSACTION_ID));
    }
}
