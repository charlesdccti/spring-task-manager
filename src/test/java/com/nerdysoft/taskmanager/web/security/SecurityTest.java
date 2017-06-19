package com.nerdysoft.taskmanager.web.security;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.SecurityTestUtil;
import com.nerdysoft.taskmanager.util.TestDataUtil;
import org.junit.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityTest extends AbstractTestConfiguration {

    @Test
    public void loginSuccess() throws Exception {
        mockMvc.perform(SecurityTestUtil.LOGIN_SUCCESS)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void loginFailure() throws Exception {
        mockMvc.perform(SecurityTestUtil.LOGIN_FAILURE)
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void loginWithSuspendedUser() throws Exception {
        mockMvc.perform(SecurityTestUtil.LOGIN_WITH_SUSPENDED_USER)
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get("/api/logout")
                .with(SecurityTestUtil.userAuth(TestDataUtil.USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void csrfTokenSuccess() throws Exception {
        mockMvc.perform(get("/api/users/1")
                .with(SecurityTestUtil.userAuth(TestDataUtil.USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void csrfTokenFailure() throws Exception {
        mockMvc.perform(get("/api/users/1")
                .with(SecurityTestUtil.userAuth(TestDataUtil.USER_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
