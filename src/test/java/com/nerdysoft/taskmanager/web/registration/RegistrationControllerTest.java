package com.nerdysoft.taskmanager.web.registration;

import com.nerdysoft.taskmanager.AbstractTestConfiguration;
import com.nerdysoft.taskmanager.util.JsonTestUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.nerdysoft.taskmanager.util.TestDataUtil.INVALID_REGISTRATION_USER;
import static com.nerdysoft.taskmanager.util.TestDataUtil.REGISTRATION_USER;
import static com.nerdysoft.taskmanager.util.TestDataUtil.getUserWithOutTasks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerTest extends AbstractTestConfiguration {


    @Test
    public void registration() throws Exception {
        mockMvc.perform(post("/api/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeUserWithPassword(getUserWithOutTasks(REGISTRATION_USER))))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void registrationFailed() throws Exception {
        mockMvc.perform(post("/api/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestUtil.writeUserWithPassword(getUserWithOutTasks(INVALID_REGISTRATION_USER))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
