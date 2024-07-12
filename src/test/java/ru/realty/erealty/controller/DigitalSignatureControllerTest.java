package ru.realty.erealty.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

class DigitalSignatureControllerTest extends BaseSpringBootControllerTest {
    @SneakyThrows
    @Test
    void getGenerateUserDigitalSignatureShouldWork() {
        mockMvc.perform(MockMvcRequestBuilders.get("/generateUserDigitalSignature"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @SneakyThrows
    @Test
    void generateUserDigitalSignatureShouldWork() {
        mockMvc.perform(MockMvcRequestBuilders.post("/generateUserDigitalSignature"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}