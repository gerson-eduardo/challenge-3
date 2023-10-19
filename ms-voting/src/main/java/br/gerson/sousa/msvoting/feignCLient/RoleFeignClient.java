package br.gerson.sousa.msvoting.feignCLient;

import br.gerson.sousa.msvoting.dto.RoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-employees")
public interface RoleFeignClient {
    @GetMapping("/ms-employees/api/v1/role/employee/{cpf}")
    ResponseEntity<RoleDto> findByCpf(@PathVariable String cpf);
}
