package com.github.haseoo.courier.controllers.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haseoo.courier.commandsdata.users.clients.ClientIndividualAddCommandData;
import com.github.haseoo.courier.commandsdata.users.clients.ClientIndividualEditCommandData;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientIndividualEditData;
import com.github.haseoo.courier.services.adapters.ClientIndividualServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.haseoo.courier.testutlis.constants.Constants.INTEGRATION_TEST;
import static com.github.haseoo.courier.testutlis.generators.UserControllerTestDataGenerator.getClientIndividualData;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin1", roles = "ADMIN")
@Tag(INTEGRATION_TEST)
class ClientCompanyIndividualTest {
    @MockBean
    private ClientIndividualServiceImpl clientIndividualService;

    @MockBean
    private UserDetailsServiceImpl userDetalisService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void should_return_200_when_register_success() throws Exception {
        //given
        ClientIndividualAddCommandData in = new ClientIndividualAddCommandData("test",
                "tutu".toCharArray(),
                "test@t.co",
                "1234",
                "TestName",
                "TestSurname",
                "4556");
        when(clientIndividualService.add(any())).thenReturn(getClientIndividualData());
        ArgumentCaptor<ClientIndividualAddData> argument = ArgumentCaptor.forClass(ClientIndividualAddData.class);
        //when & then
        mockMvc.perform(put("/api/client/individual/register").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        verify(clientIndividualService).add(argument.capture());
        Assertions.assertThat(argument.getValue().getUserName()).isEqualTo(in.getUserName());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
        Assertions.assertThat(argument.getValue().getPesel()).isEqualTo(in.getPesel());

    }

    @Test
    void should_return_200_when_edit_success() throws Exception {
        //given
        final long id = 1L;
        ClientIndividualEditCommandData in = new ClientIndividualEditCommandData(
                "".toCharArray(),
                "test@t.pl",
                "1234",
                "testName",
                "testSurname"
        );
        when(clientIndividualService.edit(anyLong(), any())).thenReturn(getClientIndividualData());
        ArgumentCaptor<ClientIndividualEditData> argument = ArgumentCaptor.forClass(ClientIndividualEditData.class);
        //when & then
        mockMvc.perform(post("/api/client/individual/" + Long.toString(id)).contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        verify(clientIndividualService).edit(eq(id), argument.capture());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getName()).isEqualTo(in.getName());
        Assertions.assertThat(argument.getValue().getSurname()).isEqualTo(in.getSurname());
    }

    @Test
    void should_return_200_and_client_with_id() throws Exception {
        //given
        final long id = 1L;
        when(clientIndividualService.getById(anyLong())).thenReturn(getClientIndividualData());
        //
        mockMvc.perform(get("/api/client/individual/" + id))
                .andExpect(status().isOk());
        verify(clientIndividualService).getById(id);
    }

    @Test
    void should_return_400_when_with_id_not_exist() throws Exception {
        //given
        final long id = 1L;
        when(clientIndividualService.getById(anyLong())).thenThrow(new ClientNotFound(id));
        //
        mockMvc.perform(get("/api/client/individual/" + id))
                .andExpect(status().isNotFound());
        verify(clientIndividualService).getById(id);
    }

}