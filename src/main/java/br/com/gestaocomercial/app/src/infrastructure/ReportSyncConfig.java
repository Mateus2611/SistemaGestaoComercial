package br.com.gestaocomercial.app.src.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ReportSyncConfig {
    @Value("${report.service.url:http://localhost:8082}")
    private String reportServiceUrl;

    @Value("${report.service.token:}")
    private String serviceToken;

    @Bean
    public RestClient reportRestClient() {
        return RestClient.builder()
                .baseUrl(reportServiceUrl)
                .defaultHeader("Authorization", "Bearer " + serviceToken)
                .build();
    }
}
