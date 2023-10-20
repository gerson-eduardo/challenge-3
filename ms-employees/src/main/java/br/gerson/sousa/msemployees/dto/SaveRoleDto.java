package br.gerson.sousa.msemployees.dto;

import br.gerson.sousa.msemployees.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveRoleDto {
    private String cpf;
    private String role;
}
