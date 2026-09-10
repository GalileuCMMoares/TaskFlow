package com.webapps.taskflow.repository;

import com.webapps.taskflow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
