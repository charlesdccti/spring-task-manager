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

public class TaskValidationExceptionControllerTest extends AbstractTestConfiguration {

    @Test
    public void saveTask() throws Exception {
        mockMvc.perform(post("/api/tasks/save-for/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .content(JsonTestUtil.writeValue(INVALID_NEW_TASK)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateTask() throws Exception {
        mockMvc.perform(put("/api/tasks/update/1/for/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .content(JsonTestUtil.writeValue(INVALID_UPDATED_TASK_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shareTask() throws Exception {
        mockMvc.perform(put("/api/tasks/share/4/by-user/4/for/eric_cartman@@gmail.com")
                .with(userAuth(USER_WITH_ID_4))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
