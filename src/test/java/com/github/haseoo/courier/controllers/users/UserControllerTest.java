package com.github.haseoo.courier.controllers.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haseoo.courier.commandsdata.users.LoginCommandData;
import com.github.haseoo.courier.commandsdata.users.UserPatchCommandData;
import com.github.haseoo.courier.security.JwtAuthenticationResponse;
import com.github.haseoo.courier.services.ports.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCourierData;
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getLogisiticianData;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin1", roles = "ADMIN")
@Tag(INTEGRATION_TEST)
public class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_200_and_list_of_user() throws Exception {
        //given
        when(userService.getList()).thenReturn(Arrays.asList(getCourierData(), getLogisiticianData()));
        //when & then
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk());
    }

    @Test
    void should_activate_user() throws Exception {
        //when
        final long id = 1L;
        UserPatchCommandData in = new UserPatchCommandData(true);
        when(userService.setAsActive(anyLong())).thenReturn(getCourierData());
        //when
        mockMvc.perform(patch("/api/user/" + id).contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        //then
        verify(userService).setAsActive(id);
        verify(userService, never()).setAsInactive(anyLong());
    }

    @Test
    void should_deactivate_user() throws Exception {
        //when
        final long id = 1L;
        UserPatchCommandData in = new UserPatchCommandData(false);
        when(userService.setAsInactive(anyLong())).thenReturn(getCourierData());
        //when
        mockMvc.perform(patch("/api/user/" + id).contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        //then
        verify(userService).setAsInactive(id);
        verify(userService, never()).setAsActive(anyLong());
    }

    @Test
    void should_login_as_courier() throws Exception {
        //given
        LoginCommandData in = new LoginCommandData("test", "test".toCharArray());
        when(userService.getByUsername(anyString())).thenReturn(getCourierData());
        when(userService.getJwt(any())).thenReturn(new JwtAuthenticationResponse("testToken"));
        //when
        mockMvc.perform(post("/api/login").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        verify(userService).getByUsername(in.getUserName());
    }
}
