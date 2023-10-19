package br.gerson.sousa.msvoting.feignCLient;

import br.gerson.sousa.msvoting.dto.FindRoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-employees", url = "http://localhost:8080/ms-employees")
public interface RoleFeignClient {
    @GetMapping("/api/v1/role/employee/{cpf}")
    FindRoleDto findByCpf(@PathVariable String cpf);
}