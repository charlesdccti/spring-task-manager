package com.nerdysoft.taskmanager.web.task;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskAccessDeniedControllerTest extends AbstractTestConfiguration {

    @Test
    public void updateTask() throws Exception {
        mockMvc.perform(put("/api/tasks/update/1/for/2")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .content(JsonTestUtil.writeValue(UPDATED_TASK_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void getTask() throws Exception {
        mockMvc.perform(get("/api/tasks/get-one/6/for/2")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void getTasksForUser() throws Exception {
        mockMvc.perform(get("/api/tasks/find-all/for/1")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/delete/2/for/1")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void shareTask() throws Exception {
        mockMvc.perform(put("/api/tasks/share/1/by-user/4/for/eric_cartman@gmail.com")
                .with(userAuth(USER_WITH_ID_4))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void checkSharedTask() throws Exception {
        mockMvc.perform(put("/api/tasks/check-shared/4/for/2")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
