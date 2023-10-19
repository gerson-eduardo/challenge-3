package br.gerson.sousa.msvoting.tools;

import br.gerson.sousa.msvoting.dto.FindRoleDto;
import org.springframework.cloud.openfeign.FeignClient;

public class RoleValidation {
    public Boolean validateEmployee(FindRoleDto dto) {
        if(dto.getCpf().equals("ADMIN")){
            return true;
        }else if (dto.getCpf().equals("USER")){
            return false;
        }else{
            return null;
        }
    }
}
