package br.gerson.sousa.msemployees.controller;

import br.gerson.sousa.msemployees.dto.SaveRoleDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static br.gerson.sousa.msemployees.common.RoleConstants.*;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoleService service;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void create() throws Exception {
        SaveRoleDto dto = S_ROLE_DTO;

        Mockito.doNothing().when(service).create(dto);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/role")
                .content(objectToJson(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByCpf() {
    }

    @Test
    void update() {
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