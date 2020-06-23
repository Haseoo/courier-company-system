package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.services.ports.ClientService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UserControllerTestDataGenerator.getClientData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin1", roles = "ADMIN")
@Tag(INTEGRATION_TEST)
class ClientControllerTest {
    @MockBean
    private ClientService clientService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_200_on_get_list() throws Exception {
        //given
        when(clientService.getList()).thenReturn(Arrays.asList(getClientData()));
        //when & then
        mockMvc.perform(get("/api/client"))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_200_when_client_exists() throws Exception {
        //given
        final long id = 1L;
        when(clientService.getById(any())).thenReturn(getClientData());
        //when
        mockMvc.perform(get("/api/client/" + Long.toString(id)))
                .andExpect(status().isOk());
        //then
        verify(clientService).getById(id);
    }

    @Test
    void should_return_400_when_client_not_exist() throws Exception {
        //given
        final long id = 1L;
        when(clientService.getById(id)).thenThrow(new ClientNotFound(id));
        //when & then
        mockMvc.perform(get("/api/client/" + Long.toString(id)))
                .andExpect(status().isNotFound());
    }
}