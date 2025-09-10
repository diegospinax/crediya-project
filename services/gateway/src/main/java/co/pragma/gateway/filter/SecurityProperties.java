package co.pragma.gateway.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> publicRoutes;

    public List<String> getPublicRoutes() {
        System.out.println("Rutas: " + publicRoutes);
        return publicRoutes;
    }

    public void setPublicRoutes(List<String> publicRoutes) {
        this.publicRoutes = publicRoutes;
    }
}
