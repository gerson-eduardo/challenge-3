package br.gerson.sousa.msemployees.repository;

import br.gerson.sousa.msemployees.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static br.gerson.sousa.msemployees.common.EmployeeConstants.EMPLOYEE;
import static br.gerson.sousa.msemployees.common.EmployeeConstants.EMPLOYEE_LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeRepositoryTest {

    @Mock
    EmployeeRepository repository;

    @Test
    public void save(){
        when(repository.save(EMPLOYEE)).thenReturn(EMPLOYEE);
        Employee newEmployee = repository.save(EMPLOYEE);

        verify(repository, atLeast(1)).save(EMPLOYEE);
        assertEquals(EMPLOYEE.getId(), newEmployee.getId());
    }

    @Test
    public void findAll(){
        when(repository.findAll()).thenReturn(EMPLOYEE_LIST);
        List<Employee> list= repository.findAll();

        verify(repository, atLeast(1)).findAll();
        assertNotNull(list);
        assertEquals(EMPLOYEE_LIST, list);
    }

    @Test
    public void findById(){
        Employee employee = EMPLOYEE;
        when(repository.findById(1L)).thenReturn(Optional.of(EMPLOYEE));
        Optional<Employee> newEmployee = repository.findById(1L);

        verify(repository, atLeast(1)).findById(1L);
        assertFalse(newEmployee.isEmpty());
        assertEquals(EMPLOYEE.getName(), newEmployee.get().getName());
    }

    @Test
    public void findByCpf(){
        Employee employee = EMPLOYEE;
        when(repository.findByCpf("25369242038")).thenReturn(Optional.of(EMPLOYEE));
        Optional<Employee> newEmployee = repository.findByCpf("25369242038");

        verify(repository, atLeast(1)).findByCpf("25369242038");
        assertFalse(newEmployee.isEmpty());
        assertEquals(EMPLOYEE.getCpf(), newEmployee.get().getCpf());
    }
    @Test
    public void findByEmail(){
        Employee employee = EMPLOYEE;
        when(repository.findByEmail("jinbei@email.com")).thenReturn(Optional.of(EMPLOYEE));
        Optional<Employee> newEmployee = repository.findByEmail("jinbei@email.com");

        verify(repository, atLeast(1)).findByEmail("jinbei@email.com");
        assertFalse(newEmployee.isEmpty());
        assertEquals(EMPLOYEE.getEmail(), newEmployee.get().getEmail());
    }

    @Test
    public void deleteById(){
        when(repository.findById(1L)).thenReturn(Optional.of(EMPLOYEE));

        Optional<Employee> emp = repository.findById(1L);
        assertEquals(EMPLOYEE, emp.get());

        when(repository.findById(1L)).thenReturn(Optional.empty());

        repository.deleteById(1L);
        Optional<Employee> new_emp = repository.findById(1L);

        verify(repository, atLeast(2)).findById(1L);
        verify(repository, atLeast(1)).deleteById(1L);
        assertTrue(new_emp.isEmpty());
    }

    @Test
    public void deleteByCpf(){
        when(repository.findByCpf("25369242038")).thenReturn(Optional.of(EMPLOYEE));
        Optional<Employee> emp = repository.findByCpf("25369242038");
        assertEquals(EMPLOYEE, emp.get());

        when(repository.findByCpf("25369242038")).thenReturn(Optional.empty());

        repository.deleteByCpf("25369242038");
        Optional<Employee> new_emp = repository.findByCpf("25369242038");

        verify(repository, atLeast(2)).findByCpf("25369242038");
        verify(repository, atLeast(1)).deleteByCpf("25369242038");
        assertTrue(new_emp.isEmpty());
    }
}
