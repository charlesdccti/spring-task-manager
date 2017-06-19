package com.nerdysoft.taskmanager.web.admin;

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

public class AdminAccessDeniedControllerTest extends AbstractTestConfiguration {

    @Test
    public void adminCreateUser() throws Exception {
        mockMvc.perform(post("/api/admin/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf())
                .content(JsonTestUtil.writeUserWithPassword(getUserWithOutTasks(CREATE_NEW_USER))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminUpdateUser() throws Exception {
        mockMvc.perform(put("/api/admin/update-user/6")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .content(JsonTestUtil.writeUserWithPassword(getUserWithOutTasks(UPDATED_USER_WITH_ID_6))))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/admin/delete-user/9")
                .with(userAuth(USER_WITH_ID_3))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminSuspendUser() throws Exception {
        mockMvc.perform(put("/api/admin/suspend-user/10")
                .with(userAuth(USER_WITH_ID_4))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminUnSuspendUser() throws Exception {
        mockMvc.perform(put("/api/admin/unsuspend-user/6")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminCreateTask() throws Exception {
        mockMvc.perform(post("/api/admin/create-task-by/barbara_stevens@gmail.com/for/6")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .content(JsonTestUtil.writeValue(NEW_TASK)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminUpdateTask() throws Exception {
        mockMvc.perform(put("/api/admin/update-task/7/by/barbara_stevens@gmail.com/for/6")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf())
                .content(JsonTestUtil.writeValue(UPDATED_TASK_WITH_ID_6)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminGetUserWithTasks() throws Exception {
        mockMvc.perform(get("/api/admin/get-user-with-tasks/1")
                .with(userAuth(USER_WITH_ID_3)
                ).with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminFindAllUsers() throws Exception {
        mockMvc.perform(get("/api/admin/find-all-users")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void adminFindAllTasks() throws Exception {
        mockMvc.perform(get("/api/admin/find-all-tasks")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
