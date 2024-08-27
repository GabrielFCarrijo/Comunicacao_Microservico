package br.com.microservico.product_api.modules.sales.rabbit;

import br.com.microservico.product_api.modules.sales.dto.SalesConfirmationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalesConfirmationSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    public void sendSalesConfirmationMessage(SalesConfirmationDTO salesConfirmationMessage) {
        try {
            log.info("Sending sales confirmation: {}", new ObjectMapper().writeValueAsString(salesConfirmationMessage));
            rabbitTemplate.convertAndSend(productTopicExchange, salesConfirmationKey, salesConfirmationMessage);
            log.info("Sales confirmation sent successfully");
        } catch (Exception e ) {
            log.error("An error occurred while sending sales confirmation: {}", e.getMessage());
        }
    }
}
