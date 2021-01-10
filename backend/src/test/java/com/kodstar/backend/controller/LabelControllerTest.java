package com.kodstar.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.service.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Collections;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class LabelControllerTest {

  private Label label;

  @MockBean
  private LabelService labelService;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp(){
    this.label = new Label();
    label.setId(1L);
    label.setName("backend");
    label.setColor("fe642e");
  }

  @Test
  @DisplayName("Test createLabel Success")
  void createLabel() throws Exception {
    // Setup our mocked service
    when(labelService.saveLabelEntity(any())).thenReturn(label);

    // Execute the POST request
    mockMvc.perform(post("/label")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(label)))

            // Validate the response code and content type
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is(label.getName())))
            .andExpect(jsonPath("$.color", is(label.getColor())));
  }

  @Test
  @DisplayName("Test labelFindById")
  void getLabelById() throws Exception {

    // Setup our mocked service
    when(labelService.findById(1L)).thenReturn(label);

    // Execute the GET request
    mockMvc.perform(get("/label/{id}", 1))

            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is(label.getName())))
            .andExpect(jsonPath("$.color", is(label.getColor())));
  }

  @Test
  @DisplayName("Test getAllLabels Success")
  void getLabels() throws Exception{

    Label label1 = new Label();
    label1.setId(2L);
    label1.setName("bug");
    label1.setColor("045fb4");

    // Setup our mocked service
    when(labelService.getAllLabels()).thenReturn(Arrays.asList(label,label1));

    // Execute the GET request
    mockMvc.perform(get("/labels"))

            // Validate the response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))

            // Validate the returned fields
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].name", is(label.getName())))
            .andExpect(jsonPath("$[0].color", is(label.getColor())))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].name", is(label1.getName())))
            .andExpect(jsonPath("$[1].color", is(label1.getColor())));
  }

  @Test
  @DisplayName("Test getAllLabels No Content")
  void getLabelsNoContent() throws Exception{
    // Setup our mocked service
    when(labelService.getAllLabels()).thenReturn(Collections.emptyList());

    // Execute the GET request
    mockMvc.perform(get("/labels"))

            // Validate the response code
            .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Test shouldVerifyInvalidLabelId")
  public void shouldVerifyInvalidLabelId() throws Exception {
    // Execute the GET request
    mockMvc.perform(get("/label/{id}", "abc")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
  }

  @Test
  @DisplayName("Test updateLabel")
  void updateLabel() throws Exception {

    // Execute the PUT request
    mockMvc.perform(put("/label/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(label))
            .accept(MediaType.APPLICATION_JSON))

            // Validate the response code
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Test deleteLabel")
  void deleteLabel() throws Exception {

    // Setup our mocked service
    doNothing().when(labelService).deleteLabel(1L);

    // Execute the DELETE request
    mockMvc.perform(delete("/label/{id}",1))
            .andExpect(status().isNoContent());

    verify(labelService, times(1)).deleteLabel(1L);

  }

  static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}