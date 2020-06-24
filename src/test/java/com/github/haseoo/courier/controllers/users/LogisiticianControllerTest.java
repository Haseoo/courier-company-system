package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.EmployeeNotFoundException;
import com.github.haseoo.courier.services.ports.LogisticianService;
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
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getLogisiticianData;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin1", roles = "ADMIN")
@Tag(INTEGRATION_TEST)
public class LogisiticianControllerTest {
    @MockBean
    private LogisticianService logisticianService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_200_and_list() throws Exception{
        //given
        when(logisticianService.getList()).thenReturn(Arrays.asList(getLogisiticianData()));
        //when & then
        mockMvc.perform(get("/api/employee/logistician"))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_200_and_ask_for_logistician_with_id() throws Exception{
        //given
        long id = 1L;
        when((logisticianService.getById(anyLong()))).thenReturn(getLogisiticianData());
        //when
        mockMvc.perform(get("/api/employee/logistician/" + id))
                .andExpect(status().isOk());
        //then
        verify(logisticianService).getById(id);
    }

    @Test
    void should_return_404_when_logistician_does_not_exist() throws Exception{
        //given
        long id = 1L;
        when(logisticianService.getById(anyLong())).thenThrow(new EmployeeNotFoundException(id));
        //when & then
        mockMvc.perform(get("/api/employee/logistician/" + id))
                .andExpect(status().isNotFound());
    }
}
