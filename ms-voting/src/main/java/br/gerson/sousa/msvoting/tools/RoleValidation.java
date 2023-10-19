package br.gerson.sousa.msvoting.tools;

import br.gerson.sousa.msvoting.dto.FindRoleDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Objects;

public class RoleValidation {
    public boolean validateEmployee(FindRoleDto dto) {
        return dto.getRole().equals("ADMIN") || dto.getRole().equals("USER");
    }
    public boolean validateAdmin(FindRoleDto dto){
        return dto.getRole().equals("ADMIN");
    }
}
