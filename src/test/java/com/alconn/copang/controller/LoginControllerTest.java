package com.alconn.copang.controller;

import com.alconn.copang.common.AuthToken;
import com.alconn.copang.common.LoginToken;
import com.alconn.copang.common.WeUser;
import com.alconn.copang.enums.Role;
import com.alconn.copang.repo.UserRepo;
import com.alconn.copang.security.CustomUserDetailsService;
import com.alconn.copang.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@AutoConfigureRestDocs
//@WebMvcTest(LoginController.class)
@SpringBootTest
class LoginControllerTest {


    @Autowired
    MockMvc mvc;


    @MockBean
    UserService service;

    @Autowired
    ObjectMapper mapper;

    @Disabled
    @Test
    void login() throws Exception {
        WeUser user = WeUser.builder()
                .email("test")
                .password("1234")
                .build();
        LoginToken token = new LoginToken();

        given(this.service.login(token)).willReturn(notNull());


        token.setEmail("test");
        token.setPassword("1234");
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(token)))
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$..access_token").exists())
                .andDo(print());
    }

    @Test
    void signup() throws Exception {
        WeUser user = WeUser.builder()
                .email("test")
                .password("1234")
                .role(Role.CLIENT)
                .build();

        mvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..email", "test").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$..uid").exists())
                .andDo(print());
    }

}