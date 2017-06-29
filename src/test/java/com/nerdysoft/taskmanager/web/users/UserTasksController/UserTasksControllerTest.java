package com.nerdysoft.taskmanager.web.users.UserTasksController;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Collections;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTasksControllerTest extends AbstractTestConfiguration {

    @Test
    public void test_1_getTasksForUser() throws Exception {
        mockMvc.perform(get("/api/users/7/tasks")
                .with(userAuth(USER_WITH_ID_7))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(Collections.singleton(TASK_WITH_ID_5)))));
    }

    @Test
    public void test_2_deleteTasksForUser() throws Exception {
        mockMvc.perform(delete("/api/users/2/tasks")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
