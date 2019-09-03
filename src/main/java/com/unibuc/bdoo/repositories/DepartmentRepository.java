package com.unibuc.bdoo.repositories;

import com.unibuc.bdoo.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
