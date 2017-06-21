package com.nerdysoft.taskmanager.util;

import com.nerdysoft.taskmanager.entity.Role;
import com.nerdysoft.taskmanager.entity.Task;
import com.nerdysoft.taskmanager.entity.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static final Task TASK_WITH_ID_1 = new Task(1, "learn Java", 20, false, true,
            "eric_cartman@gmail.com", LocalDate.of(2017, 6, 7));
    public static final Task TASK_WITH_ID_2 = new Task(2, "learn C", 15, true, false,
            "kenny_mckormick@gmail.com", LocalDate.of(2017, 6, 17));
    public static final Task TASK_WITH_ID_3 = new Task(3, "learn C++", 30, true, false,
            "kyle_broflovski@gmail.com", LocalDate.of(2017, 6, 2));
    public static final Task TASK_WITH_ID_4 = new Task(4, "learn C#", 25, false, false,
            "stanley_marsh@gmail.com", LocalDate.of(2017, 5, 29));
    public static final Task TASK_WITH_ID_6 = new Task(6, "learn Sql/noSql", 27, false, true,
            "eric_cartman@gmail.com", LocalDate.of(2017, 5, 28));

    public static final Task UPDATED_TASK_WITH_ID_1 = new Task(1, "learn Java EE", 30, false, true,
            "eric_cartman@gmail.com", LocalDate.now());
    public static final Task UPDATED_TASK_WITH_ID_6 = new Task(6, "updated test task", 90, false, true, "", LocalDate.now());


    public static final Task INVALID_UPDATED_TASK_WITH_ID_1 = new Task(1, "", 999999, false, true,
            "eric_cartman@gmail.com", LocalDate.now());
    public static final Task INVALID_NEW_TASK = new Task(0, "", -1, false, true, "", LocalDate.now());

    public static final Task NEW_TASK = new Task(0, "New test task", 20, false, true, "", LocalDate.now());

    public static final User USER_WITH_ID_1 = new User(1, "Eric", "Cartman",
            "g5j$3p4xNxW37GQw", "eric_cartman@gmail.com", true, false, LocalDate.of(2017, 5, 9),
            Collections.singleton(Role.ROLE_USER), Arrays.asList(TASK_WITH_ID_1, TASK_WITH_ID_6));
    public static final User USER_WITH_ID_2 = new User(2, "Kenny", "McKormick",
            "g5j$3p4xNxW37GQw", "kenny_mckormick@gmail.com", true, false, LocalDate.of(2017, 6, 7),
            Collections.singleton(Role.ROLE_ADMIN), Arrays.asList(TASK_WITH_ID_1, TASK_WITH_ID_2, TASK_WITH_ID_6));
    public static final User USER_WITH_ID_3 = new User(3, "Kyle", "Broflovski",
            "g5j$3p4xNxW37GQw", "kyle_broflovski@gmail.com", true, false, LocalDate.of(2017, 4, 9),
            Collections.singleton(Role.ROLE_USER), Arrays.asList(TASK_WITH_ID_3));
    public static final User USER_WITH_ID_4 = new User(4, "Stanley", "Marsh",
            "g5j$3p4xNxW37GQw", "stanley_marsh@gmail.com", true, false, LocalDate.of(2017, 6, 9),
            Collections.singleton(Role.ROLE_USER), Arrays.asList(TASK_WITH_ID_4));
    public static final User USER_WITH_ID_5 = new User(5, "Barbara", "Stevens",
            "g5j$3p4xNxW37GQw", "barbara_stevens@gmail.com", true, true, LocalDate.of(2017, 5, 11),
            Collections.singleton(Role.ROLE_ADMIN), Collections.emptyList());
    public static final User USER_WITH_ID_7 = new User(7, "Sharon", "Marsh",
            "g5j$3p4xNxW37GQw", "sharon_marsh@gmail.com", false, false, LocalDate.of(2017, 4, 8),
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());
    public static final User USER_WITH_ID_8 = new User(8, "Lien", "Cartman",
            "g5j$3p4xNxW37GQw", "lien_cartman@gmail.com", true, false, LocalDate.of(2017, 4, 8),
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());

    public static final User UPDATED_USER_WITH_ID_1 = new User(1, "Chef", "McElroy",
            "g5j$3p4xNxW37GQw", "chef_mcelroy@gmail.com", true, false, LocalDate.of(2017, 5, 9),
            Collections.singleton(Role.ROLE_USER), Arrays.asList(TASK_WITH_ID_1, TASK_WITH_ID_6));
    public static final User UPDATED_USER_WITH_ID_6 = new User(6, "Jimmy", "Volmer",
            "g5j$3p4xNxW37GQw", "jimmy_volmer@gmail.com", false, false, LocalDate.of(2017, 4, 9),
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());
    public static final User UPDATED_USER_WITH_ID_7 = new User(7, "Sharon", "Marsh",
            "js$FfER34$d3Rk", "sharon_marsh@gmail.com", true, false, LocalDate.of(2017, 5, 8),
            Collections.singleton(Role.ROLE_USER), Arrays.asList(TASK_WITH_ID_1, TASK_WITH_ID_6));

    public static final User INVALID_USER_WITH_ID_4 = new User(1, "", "",
            "g5j$3p4xNxW37GQw", "", true, false, LocalDate.of(2017, 5, 9),
            Collections.singleton(Role.ROLE_USER), Arrays.asList(TASK_WITH_ID_1, TASK_WITH_ID_6));
    public static final User INVALID_USER_WITH_ID_8 = new User(8, "Lien", "Cartman",
            "", "lien_cartman@gmail.com", true, false, LocalDate.of(2017, 4, 8),
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());
    public static final User INVALID_REGISTRATION_USER = new User(0, "", "",
            "g5j$3p4xNxW37GQw", "", true, false, LocalDate.of(2017, 5, 9),
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());

    public static final User CREATE_NEW_USER = new User(0, "Sheila", "Broflovski",
            "g5j$3p4xNxW37GQw", "sheila_broflovski@gmail.com", true, false, LocalDate.of(2017, 5, 9),
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());

    public static final User REGISTRATION_USER = new User(0, "Herbert", "Garrison",
            "g5j$3p4xNxW37GQw", "herbert_garrison@gmail.com", true, false, LocalDate.of(2017, 5, 9),
            Collections.singleton(Role.ROLE_USER), Collections.emptyList());


    public static User getUserWithOutTasks(User user) {
        User lazy = new User();
        lazy.setId(user.getId());
        lazy.setUserName(user.getUserName());
        lazy.setUserLastName(user.getUserLastName());
        lazy.setPassword(user.getPassword());
        lazy.setEmail(user.getEmail());
        lazy.setEnabled(user.getEnabled());
        lazy.setAdmin(user.getAdmin());
        lazy.setRegistered(user.getRegistered());
        return lazy;
    }

}
