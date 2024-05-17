package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.Kensyu2Application;

@AutoConfigureMockMvc
@SpringBootTest(classes = Kensyu2Application.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @Test
    @WithMockUser
    @DisplayName("topページにアクセスできるかどうか")
    void getTop() throws Exception {
        // andDo(print())でリクエスト・レスポンスを表示
        this.mvc.perform(get("/top")).andDo(print())
            .andExpect(status().isOk());
    }

}
