package br.gerson.sousa.msemployees.common;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
import br.gerson.sousa.msemployees.model.Employee;

import java.util.Arrays;
import java.util.List;

public class EmployeeConstants {
    public static final Employee EMPLOYEE = new Employee(1L, "Jinbei", "25369242038", "Fishman Island 407", "jinbei@email.com", "weak_password");
    public static final Employee EMPLOYEE2 = new Employee(1L, "Nami", "20958469075", "Cocoyasi Village 301", "nami@email.com", "lazy_password");
    public static final SaveEmployeeDto S_EMP_DTO = new SaveEmployeeDto("Jinbei", "25369242038", "Fishman Island 407", "jinbei@email.com", "weak_password");
    public static final FindEmployeeDto F_EMP_DTO = new FindEmployeeDto(EMPLOYEE);
    public static final List<Employee> EMPLOYEE_LIST = Arrays.asList(EMPLOYEE, EMPLOYEE2);
}
