package com.kl.personaddress.resouces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kl.personaddress.dto.AddressDTO;
import com.kl.personaddress.resources.AddressResource;
import com.kl.personaddress.services.AddressService;
import com.kl.personaddress.services.exceptions.DatabaseException;
import com.kl.personaddress.services.exceptions.ResourceNotFoundException;
import com.kl.personaddress.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressResource.class)
public class AddressResourceTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddressService service;
    @Autowired
    private ObjectMapper objectMapper;
    Long dependentId = 3L;
    private long existingId;
    private long nonExistingId;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        dependentId = 3L;
        existingId = 1L;
        nonExistingId = 1000L;
        addressDTO = Factory.createAddressDTO();

        Mockito.when(service.insert(any())).thenReturn(addressDTO);
        Mockito.when(service.update(eq(existingId), any())).thenReturn(addressDTO);
        Mockito.when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

        Mockito.doNothing().when(service).delete(existingId);
        Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        Mockito.doThrow(ResourceNotFoundException.class).when(service).findAllByPersonId(nonExistingId);
        Mockito.doThrow(DatabaseException.class).when(service).delete(dependentId);
    }

    @Test
    public void insertShouldReturnAddressDTOWhenCreateAddress() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(addressDTO);
        ResultActions result = mockMvc.perform(post("/addresses")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.zipCode").exists());
        result.andExpect(jsonPath("$.number").exists());
        result.andExpect(jsonPath("$.city").exists());
        result.andExpect(jsonPath("$.mainAddress").exists());
        result.andExpect(jsonPath("$.personId").exists());
    }

    @Test
    public void updateShouldReturnAddressDTOWhenIdExists() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(addressDTO);
        ResultActions result = mockMvc.perform(put("/addresses/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.zipCode").exists());
        result.andExpect(jsonPath("$.number").exists());
        result.andExpect(jsonPath("$.city").exists());
        result.andExpect(jsonPath("$.mainAddress").exists());
        result.andExpect(jsonPath("$.personId").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(addressDTO);
        ResultActions result = mockMvc.perform(put("/addresses/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllByPersonShouldReturnAllByPerson() throws Exception{
        mockMvc.perform(get("/addresses/{id}", dependentId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllByPersonShouldReturnNotFoundWhenPersonDoesNotExists() throws Exception{
        ResultActions result = mockMvc.perform(get("/addresses/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception{
        ResultActions result = mockMvc.perform(delete("/addresses/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        ResultActions result = mockMvc.perform(delete("/addresses/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }
}
