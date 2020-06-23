package com.github.haseoo.courier.controllers.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haseoo.courier.commandsdata.users.clients.ClientCompanyAddCommandData;
import com.github.haseoo.courier.commandsdata.users.clients.ClientCompanyEditCommandData;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.clients.ClientNotFound;
import com.github.haseoo.courier.security.UserDetailsServiceImpl;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyAddData;
import com.github.haseoo.courier.servicedata.users.clients.ClientCompanyEditData;
import com.github.haseoo.courier.services.ports.ClientCompanyService;
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
import static com.github.haseoo.courier.testutlis.generators.UserControllerTestDataGenerator.getClientCompanyData;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin1", roles = "ADMIN")
@Tag(INTEGRATION_TEST)
class ClientCompanyControllerTest {
    @MockBean
    private ClientCompanyService clientCompanyService;

    @MockBean
    private UserDetailsServiceImpl userDetalisService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void should_return_200_when_register_success() throws Exception {
        //given
        ClientCompanyAddCommandData in = new ClientCompanyAddCommandData("test",
                "tutu".toCharArray(),
                "test@t.co",
                "1234",
                "TestComp",
                "213",
                "TestName",
                "TestSurname",
                "test@t.pl",
                "4556");
        when(clientCompanyService.add(any())).thenReturn(getClientCompanyData());
        ArgumentCaptor<ClientCompanyAddData> argument = ArgumentCaptor.forClass(ClientCompanyAddData.class);
        //when & then
        mockMvc.perform(put("/api/client/company/register").contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        verify(clientCompanyService).add(argument.capture());
        Assertions.assertThat(argument.getValue().getUserName()).isEqualTo(in.getUserName());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getCompanyName()).isEqualTo(in.getCompanyName());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getRepresentativeName()).isEqualTo(in.getRepresentativeName());
        Assertions.assertThat(argument.getValue().getRepresentativeSurname()).isEqualTo(in.getRepresentativeSurname());
        Assertions.assertThat(argument.getValue().getRepresentativeEmailAddress()).isEqualTo(in.getRepresentativeEmailAddress());
        Assertions.assertThat(argument.getValue().getRepresentativePhoneNumber()).isEqualTo(in.getRepresentativePhoneNumber());

    }

    @Test
    void should_return_200_when_edit_success() throws Exception {
        //given
        final long id = 1L;
        ClientCompanyEditCommandData in = new ClientCompanyEditCommandData(
                "".toCharArray(),
                "test@t.pl",
                "1234",
                "testCompany",
                "testName",
                "testSurname",
                "test@tc.pl",
                "1233"
        );
        when(clientCompanyService.edit(anyLong(), any())).thenReturn(getClientCompanyData());
        ArgumentCaptor<ClientCompanyEditData> argument = ArgumentCaptor.forClass(ClientCompanyEditData.class);
        //when & then
        mockMvc.perform(post("/api/client/company/" + Long.toString(id)).contentType(
                MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(in)))
                .andExpect(status().isOk());
        verify(clientCompanyService).edit(eq(id), argument.capture());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getPhoneNumber()).isEqualTo(in.getPhoneNumber());
        Assertions.assertThat(argument.getValue().getCompanyName()).isEqualTo(in.getCompanyName());
        Assertions.assertThat(argument.getValue().getEmailAddress()).isEqualTo(in.getEmailAddress());
        Assertions.assertThat(argument.getValue().getRepresentativeName()).isEqualTo(in.getRepresentativeName());
        Assertions.assertThat(argument.getValue().getRepresentativeSurname()).isEqualTo(in.getRepresentativeSurname());
        Assertions.assertThat(argument.getValue().getRepresentativeEmailAddress()).isEqualTo(in.getRepresentativeEmailAddress());
        Assertions.assertThat(argument.getValue().getRepresentativePhoneNumber()).isEqualTo(in.getRepresentativePhoneNumber());
    }

    @Test
    void should_return_200_and_client_with_id() throws Exception {
        //given
        final long id = 1L;
        when(clientCompanyService.getById(anyLong())).thenReturn(getClientCompanyData());
        //
        mockMvc.perform(get("/api/client/company/" + id))
                .andExpect(status().isOk());
        verify(clientCompanyService).getById(id);
    }

    @Test
    void should_return_400_when_with_id_not_exist() throws Exception {
        //given
        final long id = 1L;
        when(clientCompanyService.getById(anyLong())).thenThrow(new ClientNotFound(id));
        //
        mockMvc.perform(get("/api/client/company/" + id))
                .andExpect(status().isNotFound());
        verify(clientCompanyService).getById(id);
    }

}