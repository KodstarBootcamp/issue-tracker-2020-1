package com.kodstar.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.service.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class LabelControllerTest {

  private Label label;

  @MockBean
  private LabelService labelService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp(){

  }

  @Test
  void createLabel() {
  }

  @Test
  void getLabelById() {
  }

  @Test
  void getLabels() {
  }

  @Test
  void updateLabel() {
  }

  @Test
  void deleteLabel() {
  }
}