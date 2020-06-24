package com.github.haseoo.courier.controllers.users;

import com.github.haseoo.courier.services.ports.EmployeeService;
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
import static com.github.haseoo.courier.testutlis.generators.UsersDataGenerator.getCourierData;
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
public class EmployeeControllerTest {
    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_200_and_employeeList() throws Exception {
        //given
        when(employeeService.getList()).thenReturn(Arrays.asList(getCourierData(), getLogisiticianData()));
        //when & then
        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_200_and_employee_by_id() throws Exception {
        //given
        final long id = 1L;
        when(employeeService.getById(anyLong())).thenReturn(getCourierData());
        //when
        mockMvc.perform(get("/api/employee/" + id))
                .andExpect(status().isOk());
        //then
        verify(employeeService).getById(id);
    }
}
