package com.nerdysoft.taskmanager.web.security;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.*;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static com.nerdysoft.taskmanager.util.TestDataUtil.UPDATED_USER_WITH_ID_10;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecurityTest extends AbstractTestConfiguration {

    @Test
    public void test_1_loginSuccess() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("email", "butters_scotch@gmail.com")
                .param("password", "g5j$3p4xNxW37GQw")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_2_loginFailure() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("email", "eric_cartmann@gmail.com")
                .param("password", "4p$4xNxW37FGw")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_3_loginWithSuspendedUser() throws Exception {
        mockMvc.perform(post("/api/login")
                .param("email", "timmy_timmy@gmail.com")
                .param("password", "g5j$3p4xNxW37GQw")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_4_logout() throws Exception {
        mockMvc.perform(get("/api/logout")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_5_getTaskForAnonymous() throws Exception {
        mockMvc.perform(get("/api/tasks/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_6_getTaskForAnonymousWithCsrfToken() throws Exception {
        mockMvc.perform(get("/api/tasks/1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_7_withOutCsrfToken() throws Exception {
        mockMvc.perform(get("/api/users/1")
                .with(userAuth(USER_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_8_invalidCsrfToken() throws Exception {
        mockMvc.perform(get("/api/users/1")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf().useInvalidToken()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_9_UserTryUpdateRowIsAdmin() throws Exception {
        mockMvc.perform(put("/api/users/10")
                .with(userAuth(USER_WITH_ID_10))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeValue(UPDATED_USER_WITH_ID_10)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(USER_WITH_ID_10))));
    }

}
