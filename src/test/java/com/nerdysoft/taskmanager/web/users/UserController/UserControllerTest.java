package com.nerdysoft.taskmanager.web.users.UserController;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static com.nerdysoft.taskmanager.util.SecurityTestUtil.userAuth;
import static com.nerdysoft.taskmanager.util.TestDataUtil.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest extends AbstractTestConfiguration {

    @Test
    public void test_1_getUsers() throws Exception {
        mockMvc.perform(get("/api/users/")
                .with(userAuth(USER_WITH_ID_5))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(Arrays.asList(
                                USER_WITH_ID_1, USER_WITH_ID_2, USER_WITH_ID_3, USER_WITH_ID_4,
                                USER_WITH_ID_5, USER_WITH_ID_6, USER_WITH_ID_7, USER_WITH_ID_8,
                                USER_WITH_ID_9, USER_WITH_ID_10, USER_WITH_ID_11)))));
    }

    @Test
    public void test_2_getUser() throws Exception {
        mockMvc.perform(get("/api/users/2")
                .with(userAuth(USER_WITH_ID_2))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(USER_WITH_ID_2))));
    }

    @Test
    public void test_3_createUser() throws Exception {
        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeUserWithPassword(CREATE_NEW_USER)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void test_4_updateUser() throws Exception {
        mockMvc.perform(put("/api/users/1")
                .with(userAuth(USER_WITH_ID_1))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeValue(UPDATED_USER_WITH_ID_1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        JsonTestUtil.writeValue(UPDATED_USER_WITH_ID_1))));
    }

    @Test
    public void test_5_userResetPassword() throws Exception {
        mockMvc.perform(patch("/api/users?email=sharon_marsh@gmail.com"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_6_userUpdatePassword() throws Exception {
        mockMvc.perform(patch("/api/users/3" +
                "?currentPassword=g5j$3p4xNxW37GQw&newPassword=NEWg5j$3p4xNxW37GQw")
                .with(userAuth(USER_WITH_ID_3))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_7_deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/4?password=g5j$3p4xNxW37GQw")
                .with(userAuth(USER_WITH_ID_4))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}