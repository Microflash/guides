package dev.mflash.guides.tokenval.introspection;

import static dev.mflash.guides.tokenval.introspection.GenericController.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GenericController.class)
@ExtendWith(SpringExtension.class)
class GenericControllerTest {

  private @Autowired MockMvc mvc;

  @Test
  @DisplayName("Should be able to access public endpoint without auth")
  void shouldBeAbleToAccessPublicEndpointWithoutAuth() throws Exception {
    MockHttpServletResponse response = mvc.perform(
        get(CONTEXT + PUBLIC_ENDPOINT))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    assertThat(response.getContentAsString()).isNotEmpty();
  }

  @Test
  @DisplayName("Should get unauthorized on private endpoint without auth")
  void shouldGetUnauthorizedOnPrivateEndpointWithoutAuth() throws Exception {
    mvc.perform(get(CONTEXT + PRIVATE_ENDPOINT))
        .andExpect(status().isUnauthorized())
        .andReturn();
  }

  @Test
  @DisplayName("Should be able to access private endpoint with auth")
  @WithMockUser(username = "oidcUser")
  void shouldBeAbleToAccessPrivateEndpointWithAuth() throws Exception {
    MockHttpServletResponse response = mvc.perform(
        get(CONTEXT + PRIVATE_ENDPOINT))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    assertThat(response.getContentAsString()).isNotEmpty();
  }

  @Test
  @DisplayName("Should get unauthorized on private scoped endpoint without auth")
  void shouldGetUnauthorizedOnPrivateScopedEndpointWithoutAuth() throws Exception {
    mvc.perform(get(CONTEXT + PRIVATE_SCOPED_ENDPOINT))
        .andExpect(status().isUnauthorized())
        .andReturn();
  }

  @Test
  @DisplayName("Should get forbidden on private scoped endpoint without scope")
  @WithMockUser(username = "oidcUser")
  void shouldGetForbiddenOnPrivateScopedEndpointWithoutScope() throws Exception {
    mvc.perform(get(CONTEXT + PRIVATE_SCOPED_ENDPOINT))
        .andExpect(status().isForbidden())
        .andReturn();
  }

  @Test
  @DisplayName("Should be able to access private scoped endpoint with proper scope")
  @WithMockUser(username = "oidcUser", authorities = { "SCOPE_read:messages" })
  void shouldBeAbleToAccessPrivateScopedEndpointWithProperScope() throws Exception {
    MockHttpServletResponse response = mvc.perform(
        get(CONTEXT + PRIVATE_SCOPED_ENDPOINT))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    assertThat(response.getContentAsString()).isNotEmpty();
  }

  @Test
  @DisplayName("Should get forbidden on private scoped endpoint without proper scope")
  @WithMockUser(username = "oidcUser", authorities = { "SCOPE_write:messages" })
  void shouldGetForbiddenOnPrivateScopedEndpointWithoutProperScope() throws Exception {
    mvc.perform(get(CONTEXT + PRIVATE_SCOPED_ENDPOINT))
        .andExpect(status().isForbidden())
        .andReturn();
  }
}
