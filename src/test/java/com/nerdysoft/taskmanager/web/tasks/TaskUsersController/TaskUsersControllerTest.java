package com.nerdysoft.taskmanager.web.tasks.TaskUsersController;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskUsersControllerTest extends AbstractTestConfiguration {

    @Test
    public void test_1_getUsersForTask() throws Exception {
        mockMvc.perform(get("/api/tasks/6/users")
                .with(userAuth(USER_WITH_ID_5))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(Arrays.asList(USER_WITH_ID_1, USER_WITH_ID_2)))));
    }

    @Test
    public void test_2_createTask() throws Exception {
        mockMvc.perform(post("/api/tasks/users/8")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_8))
                .with(csrf())
                .content(JsonTestUtil.writeValue(NEW_TASK)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(JsonTestUtil.writeValue(NEW_TASK))));
    }

    @Test
    public void test_3_shareTask() throws Exception {
        mockMvc.perform(put("/api/tasks/6/users?email=kyle_broflovski@gmail.com")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
