package br.gerson.sousa.msemployees.repository;

import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

    Role save(Role role);
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Optional<Role> findByEmployee(Employee employee);
    void deleteById(Long id);
    void deleteByEmployee(Employee employee);
}
