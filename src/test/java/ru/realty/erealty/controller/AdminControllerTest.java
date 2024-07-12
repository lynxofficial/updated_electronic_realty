package ru.realty.erealty.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class AdminControllerTest extends BaseSpringBootControllerTest {
    @SneakyThrows
    @Test
    void profileShouldWork() {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/profile"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}