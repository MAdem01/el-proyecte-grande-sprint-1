package com.codecool.codekickfc.controller;

import com.codecool.codekickfc.CodeKickFcApplication;
import com.codecool.codekickfc.dto.users.LoginRequest;
import com.codecool.codekickfc.dto.users.NewUserDTO;
import com.codecool.codekickfc.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.dto.users.UsernameDTO;
import com.codecool.codekickfc.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CodeKickFcApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties"
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmailService emailService;

    @Test
    @Order(1)
    void createUser_whenUserNotExists_thenOk() throws Exception {
        NewUserDTO newUser = new NewUserDTO(
                "username",
                "first",
                "last",
                "email@email.com",
                "password"
        );

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(2)
    void createUser_whenUserExists_thenInternalServerError() throws Exception {
        NewUserDTO newUser = new NewUserDTO(
                "username",
                "first",
                "last",
                "email@email.com",
                "password"
        );

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getAllUsers_whenAuthenticatedUser_thenOk() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(get("/api/users/all")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[-1].username").value("username"));
    }

    @Test
    void getAllUsers_whenNonAuthenticatedUser_thenUnauthorizedError() throws Exception {
        mockMvc.perform(get("/api/users/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void updateUser_whenValidIdAndAuthenticatedUser_thenOk() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();
        String id = objectMapper.readTree(response).get("id").asText();

        UpdateUserDTO update = new UpdateUserDTO(
                "updated",
                "first",
                "last",
                "email@email.com"
        );

        mockMvc.perform(put("/api/users/{userId}", id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(100L));
    }

    @Test
    void updateUser_whenValidIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        UpdateUserDTO update = new UpdateUserDTO(
                "updated",
                "first",
                "last",
                "email@email.com"
        );

        mockMvc.perform(put("/api/users/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateUser_whenInValidIdAndAuthenticatedUser_thenUserNotFoundException() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();

        UpdateUserDTO update = new UpdateUserDTO(
                "updated",
                "first",
                "last",
                "email@email.com"
        );

        mockMvc.perform(put("/api/users/1000")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_whenInValidIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        UpdateUserDTO update = new UpdateUserDTO(
                "updated",
                "first",
                "last",
                "email@email.com"
        );

        mockMvc.perform(put("/api/users/1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void deleteUser_whenValidIdAndAuthenticatedUser_thenOk() throws Exception {
        String response = getLoginResponse();
        String id = objectMapper.readTree(response).get("id").asText();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(delete("/api/users/{userId}", id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(100L));
    }

    @Test
    void deleteUser_whenValidIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/users/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteUser_whenInValidIdAndAuthenticatedUser_thenUserNotFoundException() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(delete("/api/users/1000")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_whenInValidIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/users/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserById_whenValidIdAndAuthenticatedUser_thenOk() throws Exception {
        String response = getLoginResponse();
        String id = objectMapper.readTree(response).get("id").asText();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(get("/api/users/{userId}", id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    void getUserById_whenValidIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserById_whenInValidIdAndAuthenticatedUser_thenUserNotFoundException() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(get("/api/users/1000")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserById_whenInValidIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addUserToMatch_whenValidUserIdAndValidMatchIdAndAuthenticatedUser_thenOk() throws Exception {
        String response = getLoginResponse();
        String id = objectMapper.readTree(response).get("id").asText();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(patch("/api/users/{userId}/matches/1/add", id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(100L))
                .andExpect(jsonPath("$.matchId").value(1L));
    }

    @Test
    void addUserToMatch_whenValidUserIdAndValidMatchIdAndAuthenticatedUserAndAlreadySignedUp_thenAlreadySignedUpException() throws Exception {
        String response = getLoginResponse();
        String id = objectMapper.readTree(response).get("id").asText();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(patch("/api/users/{userId}/matches/1/add", id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserToMatch_whenValidUserIdAndValidMatchIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        mockMvc.perform(patch("/api/users/100/matches/1/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addUserToMatch_whenInvalidUserIdAndValidMatchIdAndAuthenticatedUser_thenUserNotFoundException() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(patch("/api/users/1000/matches/1/add")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addUserToMatch_whenValidUserIdAndInvalidMatchIdAndAuthenticatedUser_thenMatchNotFoundException() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();
        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(patch("/api/users/{userId}/matches/1000/add", id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addUserToMatch_whenInvalidUserIdAndInvalidMatchIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        mockMvc.perform(patch("/api/users/1000/matches/1000/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void removeUserFromMatch_whenValidUserIdAndValidMatchIdAndAuthenticatedUser_thenOk() throws Exception {
        String response = getLoginResponse();
        String id = objectMapper.readTree(response).get("id").asText();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(delete("/api/users/{userId}/matches/1/remove", id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(100L))
                .andExpect(jsonPath("$.matchId").value(1L));
    }

    @Test
    void removeUserFromMatch_whenValidUserIdAndValidMatchIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/users/100/matches/1/remove")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void removeUserFromMatch_whenInvalidUserIdAndValidMatchIdAndAuthenticatedUser_thenUserNotFoundException() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();

        mockMvc.perform(delete("/api/users/1000/matches/1/remove")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeUserFromMatch_whenValidUserIdAndInvalidMatchIdAndAuthenticatedUser_thenMatchNotFoundException() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();
        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(delete("/api/users/{userId}/matches/1000/remove", id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeUserFromMatch_whenInvalidUserIdAndInvalidMatchIdAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/users/1000/matches/1000/remove")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addAdmin_whenValidUserAndAuthenticatedUser_thenOk() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();

        UsernameDTO username = new UsernameDTO("username");

        mockMvc.perform(post("/api/users/admin/add")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(username)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(100L));
    }

    @Test
    void addAdmin_whenInvalidUserAndAuthenticatedUser_thenUserNotFoundException() throws Exception {
        String response = getLoginResponse();
        String token = objectMapper.readTree(response).get("jwt").asText();

        UsernameDTO username = new UsernameDTO("ItTest");

        mockMvc.perform(post("/api/users/admin/add")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(username)))
                .andExpect(status().isNotFound());
    }

    @Test
    void addAdmin_whenValidUserAndNotAuthenticatedUser_thenUnauthorized() throws Exception {
        UsernameDTO username = new UsernameDTO("ItTest");

        mockMvc.perform(post("/api/users/admin/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(username)))
                .andExpect(status().isUnauthorized());
    }

    private String getLoginResponse() throws Exception {
        LoginRequest userSignInRequest = new LoginRequest(
                "username", "password");

        return mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userSignInRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}