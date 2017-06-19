package com.nerdysoft.taskmanager.repository;

import com.nerdysoft.taskmanager.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @EntityGraph(value = User.GRAPH_WITH_TASKS)
    @Query("SELECT u FROM User u WHERE u.id=:id")
    User getOneWithTasks(@Param("id") Integer id);

}
