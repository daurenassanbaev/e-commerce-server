package com.ecommerce.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "security.keycloak")
public class SecurityProperties {
    private String baseUrl;
    private String realm;
    private Client clientUser;
    private AdminClient clientAdmin;

    @Data
    public static class Client {
        private String id;
        private String secret;
    }

    @Data
    public static class AdminClient {
        private String id;
        private String secret;
        private String username;
        private String password;
    }
}