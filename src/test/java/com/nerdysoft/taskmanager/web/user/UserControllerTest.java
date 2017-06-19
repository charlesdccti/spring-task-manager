package com.nerdysoft.taskmanager.web.user;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractTestConfiguration {

    @Test
    public void updateUser() throws Exception {
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .content(JsonTestUtil.writeValue(getUserWithOutTasks(UPDATED_USER_WITH_ID_1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(getUserWithOutTasks(UPDATED_USER_WITH_ID_1)))));
    }

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get("/api/users/2")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(getUserWithOutTasks(USER_WITH_ID_2)))));
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/3/g5j$3p4xNxW37GQw")
                .with(userAuth(USER_WITH_ID_3))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void userResetPassword() throws Exception {
        mockMvc.perform(put("/api/users/reset-password-for/7/g5j$3p4xNxW37GQw")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_7))
                .with(csrf())
                .content(JsonTestUtil.writeUserWithPassword(getUserWithOutTasks(UPDATED_USER_WITH_ID_7))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void userRestorePassword() throws Exception {
        mockMvc.perform(get("/api/users/restore-password/lien_cartman@gmail.com"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
