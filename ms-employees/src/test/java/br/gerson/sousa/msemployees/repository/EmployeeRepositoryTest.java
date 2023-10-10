package br.gerson.sousa.msemployees.repository;

import br.gerson.sousa.msemployees.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.gerson.sousa.msemployees.common.EmployeeConstants.EMPLOYEE;
import static br.gerson.sousa.msemployees.common.EmployeeConstants.EMPLOYEE_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeRepositoryTest {

    @Mock
    EmployeeRepository repository;

    @Test
    public void findById(){
        Employee employee = EMPLOYEE;
        when(repository.findById(1L)).thenReturn(Optional.of(EMPLOYEE));
        Optional<Employee> newEmployee = repository.findById(1L);
        verify(repository, atLeast(1)).findById(1L);
        assertFalse(newEmployee.isEmpty());
        assertEquals(EMPLOYEE.getName(), newEmployee.get().getName());
    }
}
