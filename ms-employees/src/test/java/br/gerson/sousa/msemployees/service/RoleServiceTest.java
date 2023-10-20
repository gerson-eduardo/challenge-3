package br.gerson.sousa.msemployees.service;

import br.gerson.sousa.msemployees.dto.FindRoleDto;
import br.gerson.sousa.msemployees.dto.SaveRoleDto;
import br.gerson.sousa.msemployees.ex.EntityConflictException;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
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
@Disabled
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
    void createRoleEmployeeExists(){
        when(employeeRepository.findByCpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.of(EMPLOYEE));

        when(roleRepository.findByEmployee_Cpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.empty());

        service.create(S_ROLE_DTO);
    }

    @Test
    void createRoleEmployeeDontExists(){
        when(employeeRepository.findByCpf(S_ROLE_DTO.getCpf())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.create(S_ROLE_DTO);
        });
    }

    @Test
    void create(){
        SaveRoleDto dto = S_ROLE_DTO;
        {
            //Employee exists and role don't
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.of(EMPLOYEE));

            when(roleRepository.findByEmployee_Cpf(dto.getCpf())).thenReturn(Optional.empty());

            service.create(dto);
        }
        {
            //Employee don't exists
            dto.setCpf("71694852379");
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> {
                service.create(dto);
            });
        }
        {
            //Employee and Role already exists
            dto.setCpf(S_ROLE_DTO.getCpf());
            when(employeeRepository.findByCpf(dto.getCpf())).thenReturn(Optional.of(EMPLOYEE));
            when(roleRepository.findByEmployee_Cpf(dto.getCpf())).thenReturn(Optional.of(ROLE));

            assertThrows(EntityConflictException.class, () ->{
                service.create(dto);
            });
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
        FindRoleDto role = service.findById(1L);

        verify(roleRepository, atLeast(1)).findById(1L);
        assertEquals(ROLE.getRole(), role.getRole());
    }

    @Test
    void findByCpf() {
        when(roleRepository.findByEmployee_Cpf("25369242038")).thenReturn(Optional.of(ROLE));
        FindRoleDto role = service.findByCpf("25369242038");

        verify(roleRepository, atLeast(1)).findByEmployee_Cpf("25369242038");
        assertEquals(ROLE.getEmployee().getCpf(), role.getCpf());
    }

    @Test
    void update() {
        SaveRoleDto dto = S_ROLE_DTO;
        {
            //Employee and Role exists
            when(roleRepository.findByEmployee_Cpf(dto.getCpf())).thenReturn(Optional.of(ROLE));
            service.update(dto);
        }{
            //Role with Employee cpf don't exist
            when(roleRepository.findByEmployee_Cpf(dto.getCpf())).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> {
                service.update(dto);
            });
        }
    }

    @Test
    void deleteById() {
        {
            //Delete successfully
            doNothing().when(roleRepository).deleteById(1L);
            service.deleteById(1L);
        }
        {
            //Role not found
            doThrow(EmptyResultDataAccessException.class).when(roleRepository).deleteById(99L);

            assertThrows(EntityNotFoundException.class, () -> {
                service.deleteById(99L);
            });
        }
    }

    @Test
    void deleteByCpf() {
        {
            //Delete successfully
            doNothing().when(roleRepository).deleteByEmployee_Cpf("25369242038");
            service.deleteByCpf("25369242038");
        }
        {
            //Role not found
            doThrow(EmptyResultDataAccessException.class).when(roleRepository).deleteByEmployee_Cpf("48613579486");

            assertThrows(EntityNotFoundException.class, () -> {
                service.deleteByCpf("48613579486");
            });
        }
    }
}