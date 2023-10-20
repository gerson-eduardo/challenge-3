package br.gerson.sousa.msemployees.dto;

import br.gerson.sousa.msemployees.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FindRoleDto {
    private String role;
    private String name;
    private String cpf;

    public FindRoleDto(Role role){
        this.role = role.getRole();
        this.name = role.getEmployee().getName();
        this.cpf = role.getEmployee().getCpf();
    }
}
