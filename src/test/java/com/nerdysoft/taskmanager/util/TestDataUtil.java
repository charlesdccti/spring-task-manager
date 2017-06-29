package com.nerdysoft.taskmanager.util;

import com.nerdysoft.taskmanager.entity.Task;
import com.nerdysoft.taskmanager.entity.User;

import java.time.LocalDate;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static final Task TASK_WITH_ID_1 = new Task(1, "learn Java", 20, true,
            "eric_cartman@gmail.com", LocalDate.of(2017, 6, 7));
    public static final Task TASK_WITH_ID_2 = new Task(2, "learn C", 15, false,
            "kenny_mckormick@gmail.com", LocalDate.of(2017, 6, 17));
    public static final Task TASK_WITH_ID_3 = new Task(3, "learn C++", 30, false,
            "kyle_broflovski@gmail.com", LocalDate.of(2017, 6, 2));
    public static final Task TASK_WITH_ID_4 = new Task(4, "learn C#", 25, false,
            "stanley_marsh@gmail.com", LocalDate.of(2017, 5, 29));
    public static final Task TASK_WITH_ID_5 = new Task(5, "learn Linux", 10, false,
            "sharon_marsh@gmail.com", LocalDate.of(2017, 6, 20));
    public static final Task TASK_WITH_ID_6 = new Task(6, "learn Sql/noSql", 27, true,
            "eric_cartman@gmail.com", LocalDate.of(2017, 5, 28));
    public static final Task UPDATED_TASK_WITH_ID_1 = new Task(1, "learn Java EE", 30, true,
            "eric_cartman@gmail.com", LocalDate.now());
    public static final Task INVALID_NEW_TASK = new Task(0, null, null, null,
            null, LocalDate.now());
    public static final Task INVALID_UPDATED_TASK_WITH_ID_1 = new Task(1, "", 999999, true,
            "eric_cartman@gmail.com", LocalDate.now());
    public static final Task NEW_TASK = new Task(7, "New test task", 20, true,
            "lien_cartman@gmail.com", LocalDate.now());

    public static final User USER_WITH_ID_1 = new User(1, "Eric", "Cartman",
            "g5j$3p4xNxW37GQw", "eric_cartman@gmail.com", true, false, LocalDate.of(2017, 5, 9));
    public static final User USER_WITH_ID_2 = new User(2, "Kenny", "McKormick",
            "g5j$3p4xNxW37GQw", "kenny_mckormick@gmail.com", true, false, LocalDate.of(2017, 6, 7));
    public static final User USER_WITH_ID_3 = new User(3, "Kyle", "Broflovski",
            "g5j$3p4xNxW37GQw", "kyle_broflovski@gmail.com", true, false, LocalDate.of(2017, 4, 9));
    public static final User USER_WITH_ID_4 = new User(4, "Stanley", "Marsh",
            "g5j$3p4xNxW37GQw", "stanley_marsh@gmail.com", true, false, LocalDate.of(2017, 6, 9));
    public static final User USER_WITH_ID_5 = new User(5, "Barbara", "Stevens",
            "g5j$3p4xNxW37GQw", "barbara_stevens@gmail.com", true, true, LocalDate.of(2017, 5, 11));
    public static final User USER_WITH_ID_6 = new User(6, "Token", "Black",
            "g5j$3p4xNxW37GQw", "token_black@gmail.com", false, false, LocalDate.of(2017, 4, 9));
    public static final User USER_WITH_ID_7 = new User(7, "Sharon", "Marsh",
            "g5j$3p4xNxW37GQw", "sharon_marsh@gmail.com", true, false, LocalDate.of(2017, 4, 8));
    public static final User USER_WITH_ID_8 = new User(8, "Lien", "Cartman",
            "g5j$3p4xNxW37GQw", "lien_cartman@gmail.com", true, false, LocalDate.of(2017, 4, 8));
    public static final User USER_WITH_ID_9 = new User(9, "Butters", "Scotch",
            "g5j$3p4xNxW37GQw", "butters_scotch@gmail.com", true, false, LocalDate.of(2017, 4, 8));
    public static final User USER_WITH_ID_10 = new User(10, "Ike", "Broflovski",
            "g5j$3p4xNxW37GQw", "ike_broflovski@gmail.com", true, false, LocalDate.of(2017, 4, 8));
    public static final User USER_WITH_ID_11 = new User(11, "Timmy", "Timmy",
            "g5j$3p4xNxW37GQw", "timmy_timmy@gmail.com", false, false, LocalDate.of(2017, 5, 9));
    public static final User CREATE_NEW_USER = new User(0, "Herbert", "Garrison",
            "g5j$3p4xNxW37GQw", "herbert_garrison@gmail.com", true, false, LocalDate.of(2017, 5, 9));
    public static final User UPDATED_USER_WITH_ID_1 = new User(1, "Chef", "McElroy",
            "g5j$3p4xNxW37GQw", "chef_mcelroy@gmail.com", true, false, LocalDate.of(2017, 5, 9));
    public static final User UPDATED_USER_WITH_ID_3 = new User(3, "Kyle", "Broflovski",
            "g5j$3p4xNxW37GQw", "kyle_broflovski@gmail.com", true, true, LocalDate.of(2017, 4, 9));
    public static final User INVALID_USER_WITH_ID_4 = new User(1, null, null,
            null, "", true, false, LocalDate.of(2017, 5, 9));
    public static final User INVALID_NEW_USER = new User(0, "", "",
            "", "", true, false, LocalDate.of(2017, 5, 9));

}
