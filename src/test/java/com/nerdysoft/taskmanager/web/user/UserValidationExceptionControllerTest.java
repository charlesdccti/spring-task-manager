package com.nerdysoft.taskmanager.web.user;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserValidationExceptionControllerTest extends AbstractTestConfiguration {

    @Test
    public void updateUser() throws Exception {
        mockMvc.perform(put("/api/users/4")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_4))
                .with(csrf())
                .content(JsonTestUtil.writeValue(getUserWithOutTasks(INVALID_USER_WITH_ID_4))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/2/4p4xNxW37FGw")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void userResetPassword() throws Exception {
        mockMvc.perform(put("/api/users/reset-password-for/8/g5j$3p4xNxW37GQw")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(USER_WITH_ID_8))
                .with(csrf())
                .content(JsonTestUtil.writeUserWithPassword(getUserWithOutTasks(INVALID_USER_WITH_ID_8))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void userRestorePassword() throws Exception {
        mockMvc.perform(get("/api/users/restore-password/lien_cartman--@--gmail.com"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
