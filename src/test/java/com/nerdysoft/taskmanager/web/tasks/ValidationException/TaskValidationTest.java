package com.nerdysoft.taskmanager.web.tasks.ValidationException;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static com.nerdysoft.taskmanager.util.TestDataUtil.USER_WITH_ID_1;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskValidationTest extends AbstractTestConfiguration {

    @Test
    public void test_1_updateTask() throws Exception {
        mockMvc.perform(put("/api/tasks/3")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeValue(INVALID_UPDATED_TASK_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_2_createTask() throws Exception {
        mockMvc.perform(post("/api/tasks/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .content(JsonTestUtil.writeValue(INVALID_NEW_TASK)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_3_shareTask() throws Exception {
        mockMvc.perform(put("/api/tasks/6/users?email=kyle_broflovski@gmail.com.")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
