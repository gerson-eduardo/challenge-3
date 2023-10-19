package br.gerson.sousa.msvoting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindRoleDto {
    private String role;
    private String name;
    private String cpf;
}
