package br.gerson.sousa.msemployees.repository;

import br.gerson.sousa.msemployees.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static br.gerson.sousa.msemployees.common.RoleConstants.ROLE_LIST;
import static org.junit.jupiter.api.Assertions.*;
import static br.gerson.sousa.msemployees.common.RoleConstants.ROLE;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryTest {

    @Mock
    RoleRepository repository;

    @Test
    void save() {
        when(repository.save(ROLE)).thenReturn(ROLE);
        Role role = repository.save(ROLE);

        verify(repository, atLeast(1)).save(ROLE);
        assertEquals(ROLE, role);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(ROLE_LIST);
        List<Role> list = repository.findAll();

        verify(repository, atLeast(1)).findAll();
        assertEquals(ROLE_LIST, list);
    }

    @Test
    void findById() {
        when(repository.findById(1L)).thenReturn(Optional.of(ROLE));
        Optional<Role> role = repository.findById(1L);

        verify(repository, atLeast(1)).findById(1L);
        assertTrue(role.isPresent());
        assertEquals(ROLE, role.get());
    }

    @Test
    void findByEmployee_Cpf() {
        when(repository.findByEmployee_Cpf("25369242038")).thenReturn(Optional.of(ROLE));
        Optional<Role> role = repository.findByEmployee_Cpf("25369242038");

        verify(repository, atLeast(1)).findByEmployee_Cpf("25369242038");
        assertTrue(role.isPresent());
        assertEquals(ROLE, role.get());
    }

    @Test
    void deleteById() {
        when(repository.findById(1L)).thenReturn(Optional.of(ROLE));
        Optional<Role> role = repository.findById(1L);

        assertFalse(role.isEmpty());
        assertEquals(ROLE, role.get());

        when(repository.findById(1L)).thenReturn(Optional.empty());
        Optional<Role> new_role = repository.findById(1L);

        repository.deleteById(1L);

        verify(repository, atLeast(2)).findById(1L);
        verify(repository, atLeast(1)).deleteById(1L);
        assertTrue(new_role.isEmpty());
    }

    @Test
    void deleteByEmployee_Cpf() {
        when(repository.findByEmployee_Cpf("25369242038")).thenReturn(Optional.of(ROLE));
        Optional<Role> role = repository.findByEmployee_Cpf("25369242038");

        assertFalse(role.isEmpty());
        assertEquals(ROLE, role.get());

        when(repository.findByEmployee_Cpf("25369242038")).thenReturn(Optional.empty());
        Optional<Role> new_role = repository.findByEmployee_Cpf("25369242038");

        repository.deleteByEmployee_Cpf("25369242038");

        verify(repository, atLeast(2)).findByEmployee_Cpf("25369242038");
        verify(repository, atLeast(1)).deleteByEmployee_Cpf("25369242038");
        assertTrue(new_role.isEmpty());
    }
}