package com.nerdysoft.taskmanager.web.admin;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import com.nerdysoft.taskmanager.util.SecurityTestUtil;
import com.nerdysoft.taskmanager.util.TestDataUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminValidationExceptionControllerTest extends AbstractTestConfiguration {

    @Test
    public void adminCreateUser() throws Exception {
        mockMvc.perform(post("/api/admin/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityTestUtil.userAuth(TestDataUtil.USER_WITH_ID_5))
                .with(csrf())
                .content(JsonTestUtil.writeUserWithPassword(TestDataUtil.getUserWithOutTasks(TestDataUtil.INVALID_USER_WITH_ID_4))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void adminUpdateUser() throws Exception {
        mockMvc.perform(put("/api/admin/update-user/6")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityTestUtil.userAuth(TestDataUtil.USER_WITH_ID_5))
                .with(csrf())
                .content(JsonTestUtil.writeUserWithPassword(TestDataUtil.getUserWithOutTasks(TestDataUtil.INVALID_USER_WITH_ID_8))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void adminCreateTask() throws Exception {
        mockMvc.perform(post("/api/admin/create-task-by/barbara_stevens@gmail.com/for/6")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityTestUtil.userAuth(TestDataUtil.USER_WITH_ID_5))
                .with(csrf())
                .content(JsonTestUtil.writeValue(TestDataUtil.INVALID_NEW_TASK)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void adminUpdateTask() throws Exception {
        mockMvc.perform(put("/api/admin/update-task/7/by/barbara_stevens@gmail.com/for/6")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityTestUtil.userAuth(TestDataUtil.USER_WITH_ID_5))
                .with(csrf())
                .content(JsonTestUtil.writeValue(TestDataUtil.INVALID_NEW_TASK)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
