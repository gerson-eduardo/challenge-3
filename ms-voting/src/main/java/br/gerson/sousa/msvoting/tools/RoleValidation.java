package br.gerson.sousa.msvoting.tools;

import br.gerson.sousa.msvoting.dto.FindRoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleValidation {
    public boolean validateEmployee(FindRoleDto dto) {
        return dto.getRole().equals("ADMIN") || dto.getRole().equals("USER");
    }
    public boolean validateAdmin(FindRoleDto dto){
        return dto.getRole().equals("ADMIN");
    }
}
