package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindRoleDto;
import br.gerson.sousa.msemployees.dto.SaveRoleDto;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.model.Role;
import br.gerson.sousa.msemployees.repository.EmployeeRepository;
import br.gerson.sousa.msemployees.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @Mock
    SaveRoleDto dto;
    @Mock
    List<FindRoleDto> dtoList;

    @Test
    void create() {
        int status;
        {
            //Employee dont exists
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.empty());
            status  = service.create(dto);

            verify(employeeRepository, atLeast(1)).findByCpf(dto.getCpf());
            assertEquals(404, status);
        }{
            //Role already exists
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.of(EMPLOYEE));
            when(roleRepository.findByEmployee_Cpf(dto.getCpf())).thenReturn(Optional.of(ROLE));

            status = service.create(dto);

            verify(employeeRepository, atLeast(1)).findByCpf(dto.getCpf());
            verify(roleRepository, atLeast(1)).findByEmployee_Cpf(dto.getCpf());

            assertEquals(409, status);
        }{
            SaveRoleDto dto = new SaveRoleDto(ROLE.getEmployee().getCpf(), ROLE.getRole());
            //Employee exists but role don't
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.of(EMPLOYEE));
            when(roleRepository.findByEmployee_Cpf(dto.getCpf())).thenReturn(Optional.empty());

            status = service.create(dto);

            verify(employeeRepository, atLeast(1)).findByCpf(dto.getCpf());
            verify(roleRepository, atLeast(1)).findByEmployee_Cpf(dto.getCpf());
            assertEquals(201, status);
        }
    }

    @Test
    void findAll() {
        when(roleRepository.findAll()).thenReturn(ROLE_LIST);
        dtoList = service.findAll();

        verify(roleRepository, atLeast(1)).findAll();
        assertEquals(ROLE_LIST.get(1).getRole(), dtoList.get(1).getRole());
    }

    @Test
    void findById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(ROLE));
        Optional<Role> role = service.findById(1L);

        verify(roleRepository, atLeast(1)).findById(1L);
        assertFalse(role.isEmpty());
        assertEquals(ROLE, role.get());
    }

    @Test
    void findByCpf() {
        when(roleRepository.findByEmployee_Cpf("25369242038")).thenReturn(Optional.of(ROLE));
        Optional<Role> role = service.findByCpf("25369242038");

        verify(roleRepository, atLeast(1)).findByEmployee_Cpf("25369242038");
        assertFalse(role.isEmpty());
        assertEquals(ROLE, role.get());
    }

    @Test
    void update() {
        int status;
        SaveRoleDto dto = new SaveRoleDto(ROLE.getEmployee().getCpf(), ROLE.getRole());
        {
            //Role don't exists
            when(roleRepository.findByEmployee_Cpf(dto.getCpf())).thenReturn(Optional.empty());

            status = service.update(dto);

            verify(roleRepository, atLeast(1)).findByEmployee_Cpf(dto.getCpf());
            assertEquals(404, status);
        }{
            //Role exists
            when(roleRepository.findByEmployee_Cpf(dto.getCpf())).thenReturn(Optional.of(ROLE));

            status = service.update(dto);

            verify(roleRepository, atLeast(1)).findByEmployee_Cpf(dto.getCpf());
            assertEquals(200, status);
        }
    }

    @Test
    void deleteById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(ROLE));
        Optional<Role> role = service.findById(1L);

        assertFalse(role.isEmpty());
        assertEquals(ROLE, role.get());

        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        service.deleteById(1L);
        Optional<Role> new_role = service.findById(1L);

        verify(roleRepository, atLeast(2)).findById(1L);
        verify(roleRepository, atLeast(1)).deleteById(1L);
        assertTrue(new_role.isEmpty());
    }

    @Test
    void deleteByCpf() {
        when(roleRepository.findByEmployee_Cpf("25369242038")).thenReturn(Optional.of(ROLE));
        Optional<Role> role = service.findByCpf("25369242038");

        assertFalse(role.isEmpty());
        assertEquals(ROLE, role.get());

        when(roleRepository.findByEmployee_Cpf("25369242038")).thenReturn(Optional.empty());
        service.deleteById(1L);
        Optional<Role> new_role = service.findByCpf("25369242038");

        verify(roleRepository, atLeast(2)).findByEmployee_Cpf("25369242038");
        verify(roleRepository, atLeast(1)).deleteById(1L);
        assertTrue(new_role.isEmpty());
    }
}