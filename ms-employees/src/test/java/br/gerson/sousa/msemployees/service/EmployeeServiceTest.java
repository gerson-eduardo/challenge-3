package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
import br.gerson.sousa.msemployees.mapper.EmployeeMapper;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.model.Role;
import br.gerson.sousa.msemployees.repository.EmployeeRepository;
import br.gerson.sousa.msemployees.repository.RoleRepository;
import jakarta.ws.rs.NotFoundException;
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
    @Mock
    SaveEmployeeDto dto;
    @Mock
    FindEmployeeDto dto_list;

    @Test
    void create() {
        int result;
        {
            //Already Exists
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.of(EMPLOYEE));
            result = service.create(dto);

            verify(employeeRepository, atLeast(1)).findByCpf(dto.getCpf());
            assertEquals(409, result);
        }
        {
            //Don't Exists
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.empty());

            result = service.create(dto);

            verify(employeeRepository, atLeast(1)).findByCpf(dto.getCpf());
            assertEquals(201, result);
        }
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
    void findById() {
        {
           //Employee Exists
            when(employeeRepository.findById(1L)).thenReturn(Optional.of(EMPLOYEE));

            FindEmployeeDto dto = service.findById(1L);

            verify(employeeRepository, atLeast(1)).findById(1L);

            assertEquals(EMPLOYEE.getCpf(), dto.getCpf());
        }{
            //Employee does not exists
            //when(employeeRepository.findById(1L)).thenThrow(Exception.class);
            //FindEmployeeDto dto = service.findById(1L);
            //verify(employeeRepository, atLeast(1)).findById(1L);
        }
    }

    @Test
    void findByEmail() {
        {
            //Employee Exists
            when(employeeRepository.findByEmail("jinbei@email.com")).thenReturn(Optional.of(EMPLOYEE));

            FindEmployeeDto dto = service.findByEmail("jinbei@email.com");

            verify(employeeRepository, atLeast(1)).findByEmail("jinbei@email.com");
            assertEquals(EMPLOYEE.getEmail(), dto.getEmail());
        }
    }

    @Test
    void findByCpf() {
        {
            //Employee Exists
            when(employeeRepository.findByCpf("25369242038")).thenReturn(Optional.of(EMPLOYEE));

            FindEmployeeDto dto = service.findByCpf("25369242038");

            verify(employeeRepository, atLeast(1)).findByCpf("25369242038");
            assertEquals(EMPLOYEE.getCpf(), dto.getCpf());
        }
    }

    @Test
    void update() {
        int result;
        {
            //Don't Exists
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.empty());

            result = service.update(dto);

            verify(employeeRepository, atLeast(1)).findByCpf(dto.getCpf());
            assertEquals(204, result);
        }
        {
            //Already Exists
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.of(EMPLOYEE));
            result = service.update(dto);

            verify(employeeRepository, atLeast(1)).findByCpf(dto.getCpf());
            assertEquals(200, result);
        }
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