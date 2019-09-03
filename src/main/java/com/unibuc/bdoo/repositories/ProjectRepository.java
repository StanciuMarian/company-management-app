package com.unibuc.bdoo.repositories;

import com.unibuc.bdoo.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
