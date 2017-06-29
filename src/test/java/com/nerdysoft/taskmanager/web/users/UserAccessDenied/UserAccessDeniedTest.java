package com.nerdysoft.taskmanager.web.users.UserAccessDenied;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAccessDeniedTest extends AbstractTestConfiguration {

    @Test
    public void test_1_getUsers() throws Exception {
        mockMvc.perform(get("/api/users/")
                .with(userAuth(USER_WITH_ID_9))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_2_getUser() throws Exception {
        mockMvc.perform(get("/api/users/3")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_3_updateUser() throws Exception {
        mockMvc.perform(put("/api/users/1")
                .with(userAuth(USER_WITH_ID_7))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeValue(UPDATED_USER_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_4_userUpdatePassword() throws Exception {
        mockMvc.perform(patch("/api/users/1" +
                "?currentPassword=g5j$3p4xNxW37GQw&newPassword=NEWg5j$3p4xNxW37GQw")
                .with(userAuth(USER_WITH_ID_10))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_5_deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1?password=g5j$3p4xNxW37GQw")
                .with(userAuth(USER_WITH_ID_10))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_6_getTasksForUser() throws Exception {
        mockMvc.perform(get("/api/users/3/tasks")
                .with(userAuth(USER_WITH_ID_7))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_7_deleteTasksForUser() throws Exception {
        mockMvc.perform(delete("/api/users/1/tasks")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
