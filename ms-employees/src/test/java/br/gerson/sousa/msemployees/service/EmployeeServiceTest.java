package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
import br.gerson.sousa.msemployees.ex.EntityConflictException;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
import br.gerson.sousa.msemployees.mapper.EmployeeMapper;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.model.Role;
import br.gerson.sousa.msemployees.repository.EmployeeRepository;
import br.gerson.sousa.msemployees.repository.RoleRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static br.gerson.sousa.msemployees.common.EmployeeConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    EmployeeMapper mapper;
    @InjectMocks
    EmployeeService service;

    @Test
    void createEmployeeDontExists() {
        when(employeeRepository.findByCpf(S_EMP_DTO.getCpf())).thenReturn(Optional.empty());

        service.create(S_EMP_DTO);

        verify(employeeRepository, atLeast(1)).save(any(Employee.class));
        verify(roleRepository, atLeast(1)).save(any(Role.class));
    }

    @Test
    void createEmployeeThatExists(){
        when(employeeRepository.findByCpf(S_EMP_DTO.getCpf())).thenReturn(Optional.of(new Employee()));

        assertThrows(EntityConflictException.class, () -> {
            service.create(S_EMP_DTO);
        });

        verify(employeeRepository, never()).save(any(Employee.class));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void findAll() {
        when(employeeRepository.findAll()).thenReturn(EMPLOYEE_LIST);

        List<FindEmployeeDto> dto_list = service.findAll();

        verify(employeeRepository, atLeast(1)).findAll();

        assertFalse(dto_list.isEmpty());
        assertEquals(EMPLOYEE_LIST.get(1).getName(), dto_list.get(1).getName());
    }

    @Test
    void findByEmail() {
        {
            //Employee Exists
            when(employeeRepository.findByEmail("jinbei@email.com")).thenReturn(Optional.of(EMPLOYEE));

            FindEmployeeDto dto = service.findByEmail("jinbei@email.com");

            verify(employeeRepository, atLeast(1)).findByEmail("jinbei@email.com");
            assertEquals(EMPLOYEE.getEmail(), dto.getEmail());
        }{        //employee don't exists
            when(employeeRepository.findByEmail("jinbei@email.com")).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> {
                service.findByEmail("jinbei@email.com");
            });
        }
    }

    @Test
    void findById_employee_exists() {
            when(employeeRepository.findById(1L)).thenReturn(Optional.of(EMPLOYEE));

            FindEmployeeDto dto = service.findById(1L);

            assertNotNull(dto);

            verify(employeeRepository, atLeast(1)).findById(1L);
            assertEquals(EMPLOYEE.getName(), dto.getName());
    }

    @Test
    void findById_employee_dont_exists() {
            when(employeeRepository.findById(99L)).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> {
                service.findById(99L);
            });
    }

    @Test
    void findByCpf_employee_exists() {
            when(employeeRepository.findByCpf("25369242038")).thenReturn(Optional.of(EMPLOYEE));

            FindEmployeeDto dto = service.findByCpf("25369242038");

            verify(employeeRepository, atLeast(1)).findByCpf("25369242038");
            assertEquals(EMPLOYEE.getCpf(), dto.getCpf());
    }

    @Test
    void findByCpf_employee_dont_exists() {
            when(employeeRepository.findByCpf("25369242038")).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> {
                service.findByCpf("25369242038");
            });
    }

    @Test
    void update_employee_exists() {
        when(employeeRepository.findByCpf(S_EMP_DTO.getCpf())).thenReturn(Optional.of(EMPLOYEE));

        assertDoesNotThrow(() ->{
            service.update(S_EMP_DTO);
        });

        verify(employeeRepository, times(1)).save(EMPLOYEE);
    }

    @Test
    void update_employee_dont_exists() {
        when(employeeRepository.findByCpf(S_EMP_DTO.getCpf())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->{
            service.update(S_EMP_DTO);
        });

        verify(employeeRepository, never()).save(EMPLOYEE);

    }

    @Test
    void deleteById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(EMPLOYEE));
        FindEmployeeDto dto = service.findById(1L);

        assertEquals(EMPLOYEE.getAddress(), dto.getAddress());

        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        service.deleteById(1L);

        Optional<Employee> emp = employeeRepository.findById(1L);

        assertTrue(emp.isEmpty());
    }

    @Test
    void deleteByCpf() {
        when(employeeRepository.findByCpf("25369242038")).thenReturn(Optional.of(EMPLOYEE));
        FindEmployeeDto dto = service.findByCpf("25369242038");

        assertEquals(EMPLOYEE.getCpf(), dto.getCpf());

        when(employeeRepository.findByCpf("25369242038")).thenReturn(Optional.empty());
        service.deleteByCpf("25369242038");

        Optional<Employee> emp = employeeRepository.findByCpf("25369242038");

        verify(employeeRepository, atLeast(2)).findByCpf("25369242038");
        verify(employeeRepository, atLeast(1)).deleteByCpf("25369242038");
        assertTrue(emp.isEmpty());
    }
}