package com.nerdysoft.taskmanager.web.task;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest extends AbstractTestConfiguration {

    @Test
    public void saveTask() throws Exception {
        mockMvc.perform(post("/api/tasks/save-for/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .content(JsonTestUtil.writeValue(NEW_TASK)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTask() throws Exception {
        mockMvc.perform(put("/api/tasks/update/1/for/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .content(JsonTestUtil.writeValue(UPDATED_TASK_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(JsonTestUtil.writeValue(UPDATED_TASK_WITH_ID_1))));
    }

    @Test
    public void getTask() throws Exception {
        mockMvc.perform(get("/api/tasks/get-one/6/for/1")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(JsonTestUtil.writeValue(TASK_WITH_ID_6))));
    }

    @Test
    public void getTasksForUser() throws Exception {
        mockMvc.perform(get("/api/tasks/find-all/for/2")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(Arrays.asList(TASK_WITH_ID_1, TASK_WITH_ID_2, TASK_WITH_ID_6)))));
    }

    @Test
    public void deleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/delete/2/for/2")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shareTask() throws Exception {
        mockMvc.perform(put("/api/tasks/share/4/by-user/4/for/eric_cartman@gmail.com")
                .with(userAuth(USER_WITH_ID_4))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void checkSharedTask() throws Exception {
        mockMvc.perform(put("/api/tasks/check-shared/4/for/1")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
