package com.nerdysoft.taskmanager.web.users.UserValidationException;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserValidationTest extends AbstractTestConfiguration {

    @Test
    public void test_1_createUser() throws Exception {
        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeUserWithPassword(INVALID_NEW_USER)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_2_updateUser() throws Exception {
        mockMvc.perform(put("/api/users/8")
                .with(userAuth(USER_WITH_ID_8))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeValue(INVALID_USER_WITH_ID_4)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_3_userResetPassword() throws Exception {
        mockMvc.perform(patch("/api/users?email=sharon_marsh@gmail.com."))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_4_userUpdatePassword() throws Exception {
        mockMvc.perform(patch("/api/users/7" +
                "?currentPassword=g5j$3p4xNxW37GQw&newPassword=qwerty")
                .with(userAuth(USER_WITH_ID_7))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
