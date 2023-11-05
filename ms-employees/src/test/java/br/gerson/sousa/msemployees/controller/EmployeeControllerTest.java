package br.gerson.sousa.msemployees.controller;

import br.gerson.sousa.msemployees.dto.FindEmployeeDto;
import br.gerson.sousa.msemployees.dto.SaveEmployeeDto;
import br.gerson.sousa.msemployees.ex.EntityConflictException;
import br.gerson.sousa.msemployees.ex.EntityNotFoundException;
import br.gerson.sousa.msemployees.model.Employee;
import br.gerson.sousa.msemployees.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
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
import static br.gerson.sousa.msemployees.common.EmployeeConstants.*;
import static org.mockito.Mockito.when;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void create_valid_employee() throws Exception{
        SaveEmployeeDto dto = S_EMP_DTO;

        Mockito.doNothing().when(employeeService).create(dto);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
                .content(objectToJson(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void create_invalid_employee() throws Exception, EntityConflictException {
        SaveEmployeeDto dto = S_EMP_DTO;

        Mockito.doThrow(EntityConflictException.class).when(employeeService).create(dto);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employee")
                        .content(objectToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void findAll() {
    }

    @Test
    void findById_employee_exists() throws Exception {
        FindEmployeeDto dto = F_EMP_DTO;

        when(employeeService.findById(1L)).thenReturn(dto);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jinbei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("25369242038"));
    }

    @Test
    void findById_employee_dont_exists() throws Exception, EntityNotFoundException{
        Mockito.doThrow(EntityNotFoundException.class).when(employeeService).findById(1L);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/id/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findByCpf_employee_exists() throws Exception, EntityNotFoundException{
        FindEmployeeDto dto = F_EMP_DTO;

        when(employeeService.findByCpf("25369242038")).thenReturn(dto);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/25369242038")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jinbei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("25369242038"));
    }

    @Test
    void findByCpf_employee_dont_exists() throws Exception, EntityNotFoundException{
        FindEmployeeDto dto = F_EMP_DTO;

        Mockito.doThrow(EntityNotFoundException.class).when(employeeService).findByCpf("94685123794");

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/94685123794")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findByEmail_employee_exists() throws Exception, EntityNotFoundException {
        FindEmployeeDto dto = F_EMP_DTO;

        when(employeeService.findByEmail("jinbei@email.com")).thenReturn(dto);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/email/jinbei@email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jinbei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("25369242038"));
    }

    @Test
    void findByEmail_employee_dont_exist() throws Exception, EntityNotFoundException{
        FindEmployeeDto dto = F_EMP_DTO;

        Mockito.doThrow(EntityNotFoundException.class).when(employeeService.findByEmail("jinbei@email.com"));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/email/jinbei@email.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void update_valid_employee() throws Exception {
        SaveEmployeeDto dto = S_EMP_DTO;

        Mockito.doNothing().when(employeeService).update(dto);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employee")
                        .content(objectToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update_invalid_employee() throws Exception {
        SaveEmployeeDto dto = S_EMP_DTO;

        Mockito.doThrow(EntityNotFoundException.class).when(employeeService).update(dto);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/employee")
                        .content(objectToJson(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteByCpf_employee_exists() throws Exception {
        Mockito.doNothing().when(employeeService).deleteByCpf("49687153684");

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employee/49687153684"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    @Disabled
    @Tag("exception-needs-implementation")
    void deleteByCpf_employee_dont_exists() throws Exception, EntityNotFoundException {
        Mockito.doThrow(EntityNotFoundException.class).when(employeeService).deleteByCpf("49687153684");

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employee/49687153684"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());;
    }

    @Test
    @Tag("exception-needs-implementation")
    void deleteById() {
    }

    public String objectToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}