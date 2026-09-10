package com.webapps.taskflow.repository;

import com.webapps.taskflow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
