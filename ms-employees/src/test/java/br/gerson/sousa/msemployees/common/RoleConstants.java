package br.gerson.sousa.msemployees.common;

import br.gerson.sousa.msemployees.dto.FindRoleDto;
import br.gerson.sousa.msemployees.dto.SaveRoleDto;
import br.gerson.sousa.msemployees.model.Role;

import java.util.Arrays;
import java.util.List;

import static br.gerson.sousa.msemployees.common.EmployeeConstants.EMPLOYEE;
import static br.gerson.sousa.msemployees.common.EmployeeConstants.EMPLOYEE2;

public class RoleConstants {
    public static final Role ROLE = new Role(1L, EMPLOYEE, "ADMIN");
    public static final Role ROLE2 = new Role(2L, EMPLOYEE, "USER");
    public static final List<Role> ROLE_LIST = Arrays.asList(ROLE, ROLE2);

    public static final SaveRoleDto S_ROLE_DTO = new SaveRoleDto(EMPLOYEE.getCpf(), "USER");
    public static final FindRoleDto F_ROLE_DTO = new FindRoleDto(ROLE);
    public static final FindRoleDto F_ROLE_DTO2 = new FindRoleDto(ROLE2);
    public static final List<FindRoleDto> F_ROLE_DTO_LIST = Arrays.asList(F_ROLE_DTO, F_ROLE_DTO2);

}
