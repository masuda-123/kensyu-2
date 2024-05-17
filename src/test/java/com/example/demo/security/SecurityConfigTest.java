package com.example.demo.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.Kensyu2Application;

@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Kensyu2Application.class)
class SecurityConfigTest {
	
    @Autowired
    private MockMvc mvc;
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Test
    @WithMockUser
    @DisplayName("認証成功後にアクセスページにログインアクセスできるか確認")
    public void acccessCheckInAuth() throws Exception {
        mvc.perform(get("/top"))
                .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("認証成功後にログアウトすると、/logoutへリダイレクトする")
    @WithMockUser
    public void Logout() throws Exception {
        mvc.perform(logout("/logout"))
        	.andExpect(redirectedUrlTemplate("/login"))
        	.andExpect(status().is3xxRedirection());
    }
    
    @Test
    @DisplayName("認証されてないリクエストのとき、loginページにリダイレクトすることを検証")
    public void accessCheckInNoAuth() throws Exception {
        mvc.perform(get("/top"))
                .andExpect(header().string("Location","http://localhost/login"));
        mvc.perform(get("/login"))
                .andExpect(status().isOk());

    }
}
