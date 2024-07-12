package ru.realty.erealty.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

class AgencyControllerTest extends BaseSpringBootControllerTest {
    @SneakyThrows
    @Test
    void getAllAgenciesShouldWork() {
        mockMvc.perform(MockMvcRequestBuilders.get("/agencies"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}