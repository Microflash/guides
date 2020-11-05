package dev.mflash.guides.jwtauth.controller;

import static dev.mflash.guides.jwtauth.controller.GenericController.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class GenericControllerTest {

  private @Autowired MockMvc mvc;

  @Test
  @DisplayName("Should be able to access public endpoint without auth")
  void shouldBeAbleToAccessPublicEndpointWithoutAuth() throws Exception {
    MockHttpServletResponse response = mvc.perform(get(PUBLIC_ENDPOINT_URL))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    assertThat(response.getContentAsString()).isNotEmpty();
  }

  @Test
  @DisplayName("Should get forbidden on private endpoint without auth")
  void shouldGetForbiddenOnPrivateEndpointWithoutAuth() throws Exception {
    mvc.perform(get(PRIVATE_ENDPOINT_URL))
        .andExpect(status().isForbidden())
        .andReturn();
  }

  @Test
  @DisplayName("Should be able to access private endpoint with auth")
  @WithMockUser(username = "jwtUser")
  void shouldBeAbleToAccessPrivateEndpointWithAuth() throws Exception {
    MockHttpServletResponse response = mvc.perform(get(PRIVATE_ENDPOINT_URL))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    assertThat(response.getContentAsString()).isNotEmpty();
  }
}
