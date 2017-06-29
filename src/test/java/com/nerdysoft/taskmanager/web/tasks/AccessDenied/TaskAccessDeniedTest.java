package com.nerdysoft.taskmanager.web.tasks.AccessDenied;

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
public class TaskAccessDeniedTest extends AbstractTestConfiguration {

    @Test
    public void test_1_getTasks() throws Exception {
        mockMvc.perform(get("/api/tasks/")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_2_getTask() throws Exception {
        mockMvc.perform(get("/api/tasks/3")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_3_updateTask() throws Exception {
        mockMvc.perform(put("/api/tasks/3")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeValue(UPDATED_TASK_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_4_deleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/4")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_5_getUsersForTask() throws Exception {
        mockMvc.perform(get("/api/tasks/6/users")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void test_6_createTask() throws Exception {
        mockMvc.perform(post("/api/tasks/users/8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeValue(NEW_TASK)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_7_shareTask() throws Exception {
        mockMvc.perform(put("/api/tasks/3/users?email=kyle_broflovski@gmail.com")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
