package com.example.demo.controllers;

import com.example.demo.ApplicationConfigTest;
import com.example.demo.entities.InsuranceResult;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest extends ApplicationConfigTest {
    private static final String PATH = "/users";

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    User user = TestDataBuilder.buildUser();
    InsuranceResult insuranceResult = mock(InsuranceResult.class);

    private MockHttpServletRequestBuilder mockPostRequest
            (Object requestObject) throws Exception {
        return MockMvcRequestBuilders
                .post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestObject));
    }

    @Test
    void givenValidUser_whenCalculateRiskScore_thenReturnOk() throws Exception {
        when(userService.calculateRiskScore(user)).thenReturn(insuranceResult);

        MockHttpServletRequestBuilder mockRequest = mockPostRequest(user);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(insuranceResult)));

        verify(userService, times(1)).calculateRiskScore(user);
    }

    @Test
    void givenInvalidUser_whenCalculateRiskScore_thenThrowMethodArgumentNotValidException() throws Exception {
        user.setAge(0);
        when(userService.calculateRiskScore(user)).thenReturn(insuranceResult);

        MockHttpServletRequestBuilder mockRequest = mockPostRequest(user);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException()
                                instanceof MethodArgumentNotValidException));

        verify(userService, never()).calculateRiskScore(user);
    }

}