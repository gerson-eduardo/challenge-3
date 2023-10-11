package br.gerson.sousa.msemployees.dto;

import br.gerson.sousa.msemployees.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FindEmployeeDto {
    private String name;
    private String cpf;
    private String address;
    private String email;

    public FindEmployeeDto(Employee employee){
        this.name = employee.getName();
        this.cpf = employee.getCpf();
        this.address = employee.getAddress();
        this.email = employee.getEmail();
    }
}
