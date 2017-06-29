package com.nerdysoft.taskmanager.repository;

import com.nerdysoft.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query("SELECT t.users FROM Task t WHERE t.id=:taskId")
    List<User> findByTaskId(@Param("taskId") Integer taskId);

    @Query("SELECT (COUNT (u) > 0) FROM User u WHERE u.email=:email")
    boolean isEmailAlreadyExists(@Param("email") String email);

}
