package br.gerson.sousa.msemployees.repository;

import br.gerson.sousa.msemployees.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee save(Employee employee);
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Optional<Employee> findByCpf(String cpf);
    Optional<Employee> findByEmail(String email);
    void deleteById(Long id);
    void deleteByCpf(String cpf);
}
