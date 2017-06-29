package com.nerdysoft.taskmanager.web.tasks.TaskController;

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
public class TaskControllerTest extends AbstractTestConfiguration {

    @Test
    public void test_1_getTasks() throws Exception {
        mockMvc.perform(get("/api/tasks/")
                .with(userAuth(USER_WITH_ID_5))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(JsonTestUtil.writeValue(
                        Arrays.asList(TASK_WITH_ID_1, TASK_WITH_ID_2, TASK_WITH_ID_3,
                                TASK_WITH_ID_4, TASK_WITH_ID_5, TASK_WITH_ID_6
                        )))));
    }

    @Test
    public void test_2_getTask() throws Exception {
        mockMvc.perform(get("/api/tasks/6")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(TASK_WITH_ID_6))));
    }

    @Test
    public void test_3_updateTask() throws Exception {
        mockMvc.perform(put("/api/tasks/1")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeValue(UPDATED_TASK_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(UPDATED_TASK_WITH_ID_1))));
    }

    @Test
    public void test_4_deleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/2")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
