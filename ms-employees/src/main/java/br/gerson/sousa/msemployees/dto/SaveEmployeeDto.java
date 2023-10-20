package br.gerson.sousa.msemployees.dto;

import br.gerson.sousa.msemployees.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveEmployeeDto {
    private String name;
    private String cpf;
    private String address;
    private String email;
    private String password;

    public Employee toModel(){
        return new Employee(
                name,
                cpf,
                address,
                email,
                password
        );
    }
}
