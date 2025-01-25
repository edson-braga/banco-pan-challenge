package br.com.pan.changeadress;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@OpenAPIDefinition(info = @Info(title = "Alterar Endereço API", version = "1.0", description = "API para alterar dados cadastrais de clientes"))
@SpringBootApplication
public class ChangeAddressApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChangeAddressApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
