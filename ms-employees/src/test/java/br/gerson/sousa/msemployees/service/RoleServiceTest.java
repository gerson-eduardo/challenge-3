package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindRoleDto;
import br.gerson.sousa.msemployees.dto.SaveRoleDto;
import br.gerson.sousa.msemployees.ex.EntityConflictException;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
import br.gerson.sousa.msemployees.ex.InvalidRoleException;
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
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static br.gerson.sousa.msemployees.common.RoleConstants.*;
import static br.gerson.sousa.msemployees.common.EmployeeConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    RoleService service;

    @Test
    void createRole_employee_exists(){
        when(employeeRepository.findByCpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.of(EMPLOYEE));
        when(roleRepository.findByEmployee_Cpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> service.create(S_ROLE_DTO));
    }

    @Test
    void createRole_employee_dont_exists(){
        when(employeeRepository.findByCpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.create(S_ROLE_DTO);
        });
    }

    @Test
    void createRole_employee_and_role_allready_exists(){
        when(employeeRepository.findByCpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.of(EMPLOYEE));
        when(roleRepository.findByEmployee_Cpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.of(ROLE));

        assertThrows(EntityConflictException.class, () -> service.create(S_ROLE_DTO));
    }

    @Test
    void findAll() {
        when(roleRepository.findAll()).thenReturn(ROLE_LIST);
        List<FindRoleDto> dtoList = service.findAll();

        verify(roleRepository, atLeast(1)).findAll();
        assertEquals(ROLE_LIST.get(1).getRole(), dtoList.get(1).getRole());
    }

    @Test
    void findById_employee_exists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(ROLE));
        FindRoleDto role = service.findById(1L);

        verify(roleRepository, atLeast(1)).findById(1L);
        assertEquals(ROLE.getRole(), role.getRole());
    }

    @Test
    void findById_employee_dont_exists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->  service.findById(1L));
    }

    @Test
    void findByCpf_employee_exists() {
        when(roleRepository.findByEmployee_Cpf("25369242038")).thenReturn(Optional.of(ROLE));
        FindRoleDto role = service.findByCpf("25369242038");

        verify(roleRepository, atLeast(1)).findByEmployee_Cpf("25369242038");
        assertEquals(ROLE.getEmployee().getCpf(), role.getCpf());
    }

    @Test
    void findByCpf_employee_dont_exists() {
        when(roleRepository.findByEmployee_Cpf("25369242038")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->  service.findByCpf("25369242038"));
    }

    @Test
    void update_employee_and_role_exists() {
        when(roleRepository.findByEmployee_Cpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.of(ROLE));
        assertDoesNotThrow(() -> service.update(S_ROLE_DTO));

        verify(roleRepository, atLeast(1)).findByEmployee_Cpf(S_ROLE_DTO.getCpf());
        verify(roleRepository, atLeast(1)).save(ROLE);
    }

    @Test
    void update_role_dont_exists() {
        when(roleRepository.findByEmployee_Cpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update(S_ROLE_DTO));

        verify(roleRepository, atLeast(1)).findByEmployee_Cpf(S_ROLE_DTO.getCpf());
        verify(roleRepository, never()).save(ROLE);
    }

    @Test
    void deleteById_role_found() {
        doNothing().when(roleRepository).deleteById(1L);
        assertDoesNotThrow(() -> service.deleteById(1L));

        verify(roleRepository, atLeast(1)).deleteById(1L);
    }

    @Test
    void deleteById_role_not_found() {
        doThrow(EmptyResultDataAccessException.class).when(roleRepository).deleteById(99L);
        assertThrows(EntityNotFoundException.class, () -> service.deleteById(99L));
    }

    @Test
    void deleteByCpf_role_found() {
        doNothing().when(roleRepository).deleteByEmployee_Cpf("25369242038");
        assertDoesNotThrow(() -> service.deleteByCpf("25369242038"));

        verify(roleRepository, atLeast(1)).deleteByEmployee_Cpf("25369242038");
    }

    @Test
    void deleteByCpf_role_not_found() {
        doThrow(EmptyResultDataAccessException.class).when(roleRepository).deleteByEmployee_Cpf("25369242038");
        assertThrows(EntityNotFoundException.class, () -> service.deleteByCpf("25369242038"));
    }
}