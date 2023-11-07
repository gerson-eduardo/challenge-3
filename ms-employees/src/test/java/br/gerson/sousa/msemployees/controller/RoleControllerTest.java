package br.gerson.sousa.msemployees.controller;

import br.gerson.sousa.msemployees.dto.FindRoleDto;
import br.gerson.sousa.msemployees.dto.SaveRoleDto;
import br.gerson.sousa.msemployees.ex.EntityConflictException;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
import br.gerson.sousa.msemployees.ex.InvalidRoleException;
import br.gerson.sousa.msemployees.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static br.gerson.sousa.msemployees.common.RoleConstants.*;
import static org.mockito.Mockito.when;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoleService service;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void create_valid_role() throws Exception {
        SaveRoleDto dto = S_ROLE_DTO;

        Mockito.doNothing().when(service).create(dto);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/role")
                .content(objectToJson(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void create_invalid_role_user_not_found() throws Exception {
        SaveRoleDto dto = S_ROLE_DTO;

        Mockito.doThrow(EntityNotFoundException.class).when(service).create(dto);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/role")
                        .content(objectToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void create_invalid_role_role_already_exists() throws Exception {
        SaveRoleDto dto = S_ROLE_DTO;

        Mockito.doThrow(EntityConflictException.class).when(service).create(dto);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/role")
                        .content(objectToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void create_invalid_role_invalid_request() throws Exception {
        SaveRoleDto dto = S_ROLE_DTO;

        Mockito.doThrow(InvalidRoleException.class).when(service).create(dto);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/role")
                        .content(objectToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void findAll_roles_exists() throws Exception{
        List<FindRoleDto> dtoList = F_ROLE_DTO_LIST;

        when(service.findAll()).thenReturn(F_ROLE_DTO_LIST);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/role")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].role").value("ADMIN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].role").value("USER"));
    }

    @Test
    void findAll_roles_unexistent() throws Exception{
        List<FindRoleDto> emptyList = new ArrayList<>();

        when(service.findAll()).thenReturn(emptyList);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/role")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    void findById_role_exists() throws Exception{
        FindRoleDto dto = F_ROLE_DTO;

        when(service.findById(1L)).thenReturn(dto);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/role/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(dto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(dto.getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(dto.getCpf()));
    }

    @Test
    void findById_role_dont_exists() throws Exception{
        FindRoleDto dto = F_ROLE_DTO;

        Mockito.doThrow(EntityNotFoundException.class).when(service).findById(1L);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/role/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findByCpf_role_exists() throws Exception{
        FindRoleDto dto = F_ROLE_DTO;

        when(service.findByCpf(dto.getCpf())).thenReturn(dto);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/role/employee/" + dto.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(dto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(dto.getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(dto.getCpf()));
    }

    @Test
    void findByCpf_role_dont_exists() throws Exception{
        FindRoleDto dto = F_ROLE_DTO;

        Mockito.doThrow(EntityNotFoundException.class).when(service).findByCpf(dto.getCpf());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/role/employee/" + dto.getCpf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void update_role_exists() throws Exception{
        SaveRoleDto dto = S_ROLE_DTO;
        String contentType = "text/plain;charset=UTF-8";

        Mockito.doNothing().when(service).update(dto);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/role")
                .content(objectToJson(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.content().string("Role updated successfully!"));
    }

    @Test
    void update_role_dont_exists() throws Exception{
        SaveRoleDto dto = S_ROLE_DTO;
        String contentType = "text/plain;charset=UTF-8";

        Mockito.doThrow(EntityNotFoundException.class).when(service).update(dto);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/role")
                .content(objectToJson(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.content().string("Role not found"));
    }

    @Test
    void update_role_dont_have_permision() throws Exception{
        SaveRoleDto dto = S_ROLE_DTO;
        String contentType = "text/plain;charset=UTF-8";

        Mockito.doThrow(InvalidRoleException.class).when(service).update(dto);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/role")
                .content(objectToJson(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.content().string("Invalid ROLE type in request"));
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteByCpf() {
    }

    public String objectToJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}