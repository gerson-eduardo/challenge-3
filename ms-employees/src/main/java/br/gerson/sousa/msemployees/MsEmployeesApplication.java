package br.gerson.sousa.msemployees;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "MS-Employees", version = "1.2", description = "Api that stores employee and role information"))
public class MsEmployeesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEmployeesApplication.class, args);
    }

}
